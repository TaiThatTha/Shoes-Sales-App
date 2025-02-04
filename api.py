from typing import Union, List, Tuple
from fastapi import FastAPI, File, UploadFile, HTTPException, Form
from mysql.connector import Error
import db
from pydantic import BaseModel
from fastapi.staticfiles import StaticFiles
import shutil
import os
from fastapi.responses import JSONResponse

app = FastAPI()


@app.get("/getAllDonHang")
def get_all_don_hang():
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor(dictionary=True)
        
        # Câu lệnh SQL để lấy danh sách sản phẩm
        sql = "SELECT * FROM DON_HANG"
        cursor.execute(sql)
        results = cursor.fetchall()

        # Đóng con trỏ và kết nối
        cursor.close()
        conn.close()

        if results:
            # Nếu có sản phẩm, trả về danh sách
            return results
        else:
            # Nếu không có sản phẩm
            return {"message": "Không có anh sản phẩm nào."}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}

@app.get("/getTongSoLuongTon")
def get_tong_so_luong_ton(maSanPham: int):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor(dictionary=True)

        # Câu lệnh SQL để tính tổng số lượng
        sql = """
        SELECT SUM(soLuongTon) AS tongSoLuongTon 
        FROM BIEN_THE_SAN_PHAM 
        WHERE maSanPham = %s
        """
        cursor.execute(sql, (maSanPham,))
        result = cursor.fetchone()

        # Đóng con trỏ và kết nối
        cursor.close()
        conn.close()

        if result and result["tongSoLuongTon"] is not None:
            # Nếu có kết quả, trả về tổng số lượng
            return {
                "maSanPham": maSanPham,
                "tongSoLuongTon": result["tongSoLuongTon"]
            }
        else:
            # Nếu không có kết quả hoặc số lượng bằng None
            return {
                "message": f"Không tìm thấy sản phẩm với maSanPham: {maSanPham}"
            }
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}

@app.get("/getAllBienThe")
def get_all_bien_the():
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor(dictionary=True)
        
        # Câu lệnh SQL để lấy danh sách sản phẩm
        sql = "SELECT * FROM BIEN_THE_SAN_PHAM"
        cursor.execute(sql)
        results = cursor.fetchall()

        # Đóng con trỏ và kết nối
        cursor.close()
        conn.close()

        if results:
            # Nếu có sản phẩm, trả về danh sách
            return results
        else:
            # Nếu không có sản phẩm
            return {"message": "Không có anh sản phẩm nào."}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}

@app.post("/themAnhBienThe")
def them_anh_bien_the(
    maSanPham: int = Form(...),  # Nhận mã sản phẩm từ form
    files: List[UploadFile] = File(...),  # Nhận danh sách file
):
    UPLOAD_FOLDER = "uploads/anh_bien_the/"
    os.makedirs(UPLOAD_FOLDER, exist_ok=True)  # Tạo thư mục nếu chưa tồn tại

    try:
        # Kết nối cơ sở dữ liệu
        conn = db.connect_to_database()
        if isinstance(conn, Error):
            raise HTTPException(status_code=500, detail=f"Lỗi kết nối cơ sở dữ liệu: {conn}")

        cursor = conn.cursor()

        file_urls = []  # Danh sách lưu đường dẫn công khai của các file

        # Lặp qua từng file và xử lý
        for file in files:
            # Tạo đường dẫn lưu file
            file_path = os.path.join(UPLOAD_FOLDER, file.filename)
            with open(file_path, "wb") as buffer:
                shutil.copyfileobj(file.file, buffer)

            # Đường dẫn công khai
            public_file_url = f"/{UPLOAD_FOLDER}{file.filename}"
            file_urls.append(public_file_url)

            # Tạo `maAnh` từ tên file hoặc UUID để tránh trùng lặp
            maAnh = file.filename[:10]

            # Lưu thông tin vào cơ sở dữ liệu
            sql = """
            INSERT INTO ANH_BIEN_THE (maAnh, maSanPham, duongDan) 
            VALUES (%s, %s, %s)
            """
            cursor.execute(sql, (maAnh, maSanPham, public_file_url))

        # Commit để lưu thay đổi
        conn.commit()

        cursor.close()
        conn.close()

        # Trả về toàn bộ danh sách URL ảnh đã tải lên
        return {
            "message": "Thêm ảnh biến thể thành công.",
            "file_urls": file_urls
        }

    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Lỗi khi thêm ảnh biến thể: {str(e)}")
    
@app.get("/getDanhSachAnhBienThe")
def get_danh_sach_anh_bien_the():
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor(dictionary=True)
        
        # Câu lệnh SQL để lấy danh sách sản phẩm
        sql = "SELECT * FROM ANH_BIEN_THE"
        cursor.execute(sql)
        results = cursor.fetchall()

        # Đóng con trỏ và kết nối
        cursor.close()
        conn.close()

        if results:
            # Nếu có sản phẩm, trả về danh sách
            return results
        else:
            # Nếu không có sản phẩm
            return {"message": "Không có anh sản phẩm nào."}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}

app.mount("/uploads", StaticFiles(directory="uploads"), name="uploads")

@app.post("/themSanPham")
def them_san_pham(
    maSanPham: int = Form(...),
    tenSanPham: str = Form(...),
    moTa: str = Form(...),
    gia: float = Form(...),
    maDanhMuc: int = Form(...),
    ngayTao: str = Form(...),
    file: UploadFile = File(...)
):
    UPLOAD_FOLDER = "uploads/"
    os.makedirs(UPLOAD_FOLDER, exist_ok=True)  # Tạo thư mục nếu chưa tồn tại

    try:
        # Lưu file ảnh vào thư mục uploads
        file_path = os.path.join(UPLOAD_FOLDER, file.filename)
        with open(file_path, "wb") as buffer:
            shutil.copyfileobj(file.file, buffer)

        # Kết nối cơ sở dữ liệu
        conn = db.connect_to_database()
        cursor = conn.cursor()

        # Kiểm tra sản phẩm đã tồn tại
        check_sql = "SELECT maSanPham FROM SAN_PHAM WHERE maSanPham = %s"
        cursor.execute(check_sql, (maSanPham,))
        result = cursor.fetchone()
        if result:
            cursor.close()
            conn.close()
            return {"message": f"Sản phẩm với mã {maSanPham} đã tồn tại."}

        # Thêm sản phẩm mới vào cơ sở dữ liệu
        public_file_url = f"/uploads/{file.filename}"  # Đường dẫn công khai
        sql = """INSERT INTO SAN_PHAM (maSanPham, tenSanPham, moTa, gia, maDanhMuc,  anhSanPham, ngayTao)
                 VALUES (%s, %s, %s, %s, %s, %s, %s)"""
        adr = (maSanPham, tenSanPham, moTa, gia, maDanhMuc, public_file_url, ngayTao)
        cursor.execute(sql, adr)
        conn.commit()

        cursor.close()
        conn.close()

        # Trả về thông tin
        return {
            "message": "Thêm sản phẩm thành công.",
            "file_url": f"https://<ngrok-url>{public_file_url}"  # Thay ngrok-url bằng URL thực tế
        }

    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Lỗi khi thêm sản phẩm: {str(e)}")


class RegisterRequest(BaseModel):
    maNguoiDung: int
    tenNguoiDung: str
    email: str
    matKhau: str
    sdt: str
    diaChiMacDinh: str
    vaiTro: str
    ngayTao: str

@app.post("/register")
def register(request: RegisterRequest):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor(dictionary=True)

        check_email_sql = "SELECT email FROM NGUOI_DUNG WHERE email = %s"
        cursor.execute(check_email_sql, (request.email,))
        result_email = cursor.fetchone()

        check_sdt_sql = "SELECT sdt FROM NGUOI_DUNG WHERE sdt = %s"
        cursor.execute(check_sdt_sql, (request.sdt,))
        result_sdt = cursor.fetchone()

        if result_email or result_sdt:
            cursor.close()
            conn.close()
            return {"message": "Email hoặc Số điện thoại đã tồn tại!"}, 409
        else:
            sql = """INSERT INTO NGUOI_DUNG (maNguoiDung, tenNguoiDung, email, matKhau, sdt, diaChiMacDinh, vaiTro, ngayTao) 
                     VALUES (%s,%s, %s, %s, %s, %s, %s, %s)"""
            adr = (request.maNguoiDung, request.tenNguoiDung, request.email, request.matKhau, request.sdt, request.diaChiMacDinh, request.vaiTro, request.ngayTao)

            try:
                cursor.execute(sql, adr)
                conn.commit()
                cursor.close()
                conn.close()
                return {"message": "Đăng ký thành công!"}, 201
            except Error as e:
                conn.close()
                return {"message": "Đăng ký thất bại: " + str(e)}, 500
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}, 500


class ChangePhoneRequest(BaseModel):
    maNguoiDung: int
    sdtMoi: str

@app.post("/change_phone")
def change_phone(request: ChangePhoneRequest):
    # Kết nối cơ sở dữ liệu
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor(dictionary=True)

        try:
            # Kiểm tra người dùng có tồn tại trong cơ sở dữ liệu
            sql_check = """
            SELECT sdt FROM NGUOI_DUNG WHERE maNguoiDung = %s
            """
            cursor.execute(sql_check, (request.maNguoiDung,))
            user = cursor.fetchone()

            if not user:
                return JSONResponse(status_code=404, content={"message": "Người dùng không tồn tại"})

            # Kiểm tra số điện thoại mới có hợp lệ không (có thể thêm các quy tắc kiểm tra như độ dài, định dạng số)
            if not request.sdtMoi.isdigit() or len(request.sdtMoi) < 10:
                return JSONResponse(status_code=400, content={"message": "Số điện thoại không hợp lệ"})

            # Cập nhật số điện thoại mới
            sql_update = """
            UPDATE NGUOI_DUNG SET sdt = %s WHERE maNguoiDung = %s
            """
            cursor.execute(sql_update, (request.sdtMoi, request.maNguoiDung))
            conn.commit()

            return JSONResponse(status_code=200, content={"message": "Đổi số điện thoại thành công"})

        except Error as e:
            return JSONResponse(status_code=500, content={"message": f"Lỗi xử lý cơ sở dữ liệu: {e}"})
        finally:
            cursor.close()
            conn.close()

    else:
        return JSONResponse(status_code=500, content={"message": "Lỗi kết nối cơ sở dữ liệu"})

class UpdateAddressRequest(BaseModel):
    maNguoiDung: int
    diaChiMacDinh: str

@app.post("/addAddress")
def add_Adress(request: UpdateAddressRequest):
    # Kết nối cơ sở dữ liệu
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor(dictionary=True)

        try:
            # Kiểm tra người dùng có tồn tại trong cơ sở dữ liệu
            sql_check = """
            SELECT diaChiMacDinh FROM NGUOI_DUNG WHERE maNguoiDung = %s
            """
            cursor.execute(sql_check, (request.maNguoiDung,))
            user = cursor.fetchone()

            if not user:
                return JSONResponse(status_code=404, content={"message": "Người dùng không tồn tại"})

            # Cập nhật địa chỉ mặc định
            sql_update = """
            UPDATE NGUOI_DUNG SET diaChiMacDinh = %s WHERE maNguoiDung = %s
            """
            cursor.execute(sql_update, (request.diaChiMacDinh, request.maNguoiDung))
            conn.commit()

            return JSONResponse(status_code=200, content={"message": "Cập nhật địa chỉ thành công"})

        except Error as e:
            return JSONResponse(status_code=500, content={"message": f"Lỗi xử lý cơ sở dữ liệu: {e}"})
        finally:
            cursor.close()
            conn.close()

    else:
        return JSONResponse(status_code=500, content={"message": "Lỗi kết nối cơ sở dữ liệu"})

# Mẫu dữ liệu yêu cầu cho việc đổi tên người dùng
class ChangeUsernameRequest(BaseModel):
    maNguoiDung: int
    tenNguoiDungMoi: str

# API đổi tên người dùng
@app.post("/change_username")
def change_username(request: ChangeUsernameRequest):
    # Kết nối cơ sở dữ liệu
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor(dictionary=True)

        try:
            # Kiểm tra người dùng có tồn tại trong cơ sở dữ liệu
            sql_check = """
            SELECT tenNguoiDung FROM NGUOI_DUNG WHERE maNguoiDung = %s
            """
            cursor.execute(sql_check, (request.maNguoiDung,))
            user = cursor.fetchone()

            if not user:
                return JSONResponse(status_code=404, content={"message": "Người dùng không tồn tại"})

            # Cập nhật tên người dùng
            sql_update = """
            UPDATE NGUOI_DUNG SET tenNguoiDung = %s WHERE maNguoiDung = %s
            """
            cursor.execute(sql_update, (request.tenNguoiDungMoi, request.maNguoiDung))
            conn.commit()

            return JSONResponse(status_code=200, content={"message": "Đổi tên người dùng thành công"})

        except Error as e:
            return JSONResponse(status_code=500, content={"message": f"Lỗi xử lý cơ sở dữ liệu: {e}"})
        finally:
            cursor.close()
            conn.close()

    else:
        return JSONResponse(status_code=500, content={"message": "Lỗi kết nối cơ sở dữ liệu"})

class ChangePasswordRequest(BaseModel):
    maNguoiDung: int
    matKhauCu: str
    matKhauMoi: str

@app.post("/change_password")
def change_password(request: ChangePasswordRequest):
    # Kết nối cơ sở dữ liệu
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor(dictionary=True)

        try:
            # Kiểm tra mật khẩu cũ
            sql_check = """
            SELECT matKhau FROM NGUOI_DUNG WHERE maNguoiDung = %s
            """
            cursor.execute(sql_check, (request.maNguoiDung,))
            user = cursor.fetchone()

            if not user:
                return JSONResponse(status_code=404, content="Người dùng không tồn tại")

            if user["matKhau"] != request.matKhauCu:
                return JSONResponse(status_code=401, content= "Mật khẩu cũ không đúng")

            # Kiểm tra nếu mật khẩu mới trùng với mật khẩu cũ
            if request.matKhauMoi == request.matKhauCu:
                return JSONResponse(status_code=400, content="Mật khẩu mới không được trùng với mật khẩu cũ")

            # Cập nhật mật khẩu mới
            sql_update = """
            UPDATE NGUOI_DUNG SET matKhau = %s WHERE maNguoiDung = %s
            """
            cursor.execute(sql_update, (request.matKhauMoi, request.maNguoiDung))
            conn.commit()

            return JSONResponse(status_code=200, content={"message": "Đổi mật khẩu thành công"})

        except Error as e:
            return JSONResponse(status_code=500, content={"message": f"Lỗi xử lý cơ sở dữ liệu: {e}"})
        finally:
            cursor.close()
            conn.close()

    else:
        return JSONResponse(status_code=500, content={"message": "Lỗi kết nối cơ sở dữ liệu"})

# Định nghĩa model để nhận JSON request
class LoginRequest(BaseModel):
    email: str
    matKhau: str

@app.post("/login")
def login(request: LoginRequest):  # Nhận dữ liệu từ body (JSON)
    # Kết nối cơ sở dữ liệu
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor(dictionary=True)

        # Truy vấn kiểm tra email và mật khẩu
        sql = """
        SELECT maNguoiDung, tenNguoiDung, vaiTro, matKhau, diaChiMacDinh, sdt, email
        FROM NGUOI_DUNG 
        WHERE email = %s AND matKhau = %s
        """
        cursor.execute(sql, (request.email, request.matKhau))
        user = cursor.fetchone()

        cursor.close()
        conn.close()

        # Kiểm tra kết quả truy vấn
        if user:
            return {"message": "Đăng nhập thành công", "user": user}
        else:
            return {"message": "Sai email hoặc mật khẩu"}, 401
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu"}, 500

@app.get("/getAllUsers")
def get_all_users():
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor(dictionary=True)
        
        # Câu lệnh SQL để lấy danh sách người dùng
        sql = "SELECT * FROM NGUOI_DUNG"
        cursor.execute(sql)
        results = cursor.fetchall()

        # Đóng con trỏ và kết nối
        cursor.close()
        conn.close()

        if results:
            # Nếu có người dùng, trả về danh sách
            return results
        else:
            # Nếu không có người dùng
            return {"message": "Không có người dùng nào."}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}

@app.get("/getDanhSachSanPham")
def get_danh_sach_san_pham():
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor(dictionary=True)
        
        # Câu lệnh SQL để lấy danh sách sản phẩm
        sql = "SELECT * FROM SAN_PHAM"
        cursor.execute(sql)
        results = cursor.fetchall()

        # Đóng con trỏ và kết nối
        cursor.close()
        conn.close()

        if results:
            # Nếu có sản phẩm, trả về danh sách
            return results
        else:
            # Nếu không có sản phẩm
            return {"message": "Không có sản phẩm nào."}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}

@app.post("/themUser")
def add_nguoi_dung(
    maNguoiDung:int,
    tenNguoiDung: str,
    email: str,
    matKhau: str,
    sdt: str,
    diaChiMacDinh: str,
    vaiTro: str,
    ngayTao: str,
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        sql = """INSERT INTO NGUOI_DUNG (maNguoiDung,tenNguoiDung, email, matKhau, sdt, diaChiMacDinh, vaiTro, ngayTao) 
                VALUES (%s, %s, %s, %s, %s, %s, %s, %s)"""
        adr = (
            maNguoiDung,
            tenNguoiDung,
            email,
            matKhau,
            sdt,
            diaChiMacDinh,
            vaiTro,
            ngayTao,
        )

        try:
            cursor.execute(sql, adr)
            conn.commit()
            # Đóng con trỏ và kết nối
            cursor.close()
            conn.close()
            return {"message": "Thêm người dùng thành công."}
        except Error as e:
            # Đóng con trỏ và kết nối
            conn.close()
            return {"message": "Thêm người dùng thất bại: " + str(e)}
    else:
        print(f"Lỗi kết nối: {conn}")

@app.delete("/xoaUser")
def delete_nguoi_dung(
    maNguoiDung: int  # Xóa người dùng dựa trên mã người dùng
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem người dùng có tồn tại không
        check_sql = "SELECT maNguoiDung FROM NGUOI_DUNG WHERE maNguoiDung = %s"
        cursor.execute(check_sql, (maNguoiDung,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy người dùng
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy người dùng với mã {maNguoiDung}."}

        # Nếu người dùng tồn tại, thực hiện xóa
        delete_sql = "DELETE FROM NGUOI_DUNG WHERE maNguoiDung = %s"
        try:
            cursor.execute(delete_sql, (maNguoiDung,))
            conn.commit()
            # Đóng con trỏ và kết nối
            cursor.close()
            conn.close()
            return {"message": "Xóa người dùng thành công."}
        except Error as e:
            # Đóng con trỏ và kết nối
            conn.close()
            return {"message": "Xóa người dùng thất bại: " + str(e)}
    else:
        print(f"Lỗi kết nối: {conn}")
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.put("/suaUser")
def update_nguoi_dung(
    maNguoiDung: int,  # Mã người dùng cần sửa
    tenNguoiDung: str = None,  # Tên người dùng mới (tùy chọn)
    email: str = None,  # Email mới (tùy chọn)
    matKhau: str = None,  # Mật khẩu mới (tùy chọn)
    sdt: str = None,  # Số điện thoại mới (tùy chọn)
    diaChiMacDinh: str = None,  # Địa chỉ mới (tùy chọn)
    vaiTro: str = None,  # Vai trò mới (tùy chọn)
    ngayTao: str = None  # Ngày tạo mới (tùy chọn)
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem người dùng có tồn tại không
        check_sql = "SELECT maNguoiDung FROM NGUOI_DUNG WHERE maNguoiDung = %s"
        cursor.execute(check_sql, (maNguoiDung,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy người dùng
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy người dùng với mã {maNguoiDung}."}

        # Tạo câu lệnh SQL động dựa trên các trường được cung cấp
        update_fields = []
        update_values = []
        if tenNguoiDung:
            update_fields.append("tenNguoiDung = %s")
            update_values.append(tenNguoiDung)
        if email:
            update_fields.append("email = %s")
            update_values.append(email)
        if matKhau:
            update_fields.append("matKhau = %s")
            update_values.append(matKhau)
        if sdt:
            update_fields.append("sdt = %s")
            update_values.append(sdt)
        if diaChiMacDinh:
            update_fields.append("diaChiMacDinh = %s")
            update_values.append(diaChiMacDinh)
        if vaiTro:
            update_fields.append("vaiTro = %s")
            update_values.append(vaiTro)
        if ngayTao:
            update_fields.append("ngayTao = %s")
            update_values.append(ngayTao)

        if not update_fields:
            # Nếu không có trường nào được cung cấp để cập nhật
            cursor.close()
            conn.close()
            return {"message": "Không có thông tin nào được cung cấp để cập nhật."}

        # Tạo câu lệnh SQL hoàn chỉnh
        update_sql = f"UPDATE NGUOI_DUNG SET {', '.join(update_fields)} WHERE maNguoiDung = %s"
        update_values.append(maNguoiDung)

        try:
            cursor.execute(update_sql, tuple(update_values))
            conn.commit()
            # Đóng con trỏ và kết nối
            cursor.close()
            conn.close()
            return {"message": "Cập nhật người dùng thành công."}
        except Error as e:
            # Đóng con trỏ và kết nối
            conn.close()
            return {"message": "Cập nhật người dùng thất bại: " + str(e)}
    else:
        print(f"Lỗi kết nối: {conn}")
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.get("/getUser")
def get_nguoi_dung(
    maNguoiDung: int  # Mã người dùng cần lấy thông tin
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor(dictionary=True)  # Sử dụng dictionary=True để trả về dữ liệu dạng dictionary
        
        # Câu lệnh SQL để lấy thông tin người dùng
        sql = "SELECT * FROM NGUOI_DUNG WHERE maNguoiDung = %s"
        cursor.execute(sql, (maNguoiDung,))
        result = cursor.fetchone()

        # Đóng con trỏ và kết nối
        cursor.close()
        conn.close()

        if result:
            # Nếu tìm thấy người dùng, trả về thông tin
            return result
        else:
            # Nếu không tìm thấy người dùng
            return {"message": f"Không tìm thấy người dùng với mã {maNguoiDung}."}
    else:
        print(f"Lỗi kết nối: {conn}")
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.get("/kiemTraVaiTroAdmin")
def kiemTraVaiTroAdmin(
    maNguoiDung: int  # Mã người dùng cần kiểm tra
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem người dùng có tồn tại không
        check_sql = "SELECT vaiTro FROM NGUOI_DUNG WHERE maNguoiDung = %s"
        cursor.execute(check_sql, (maNguoiDung,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy người dùng
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy người dùng với mã {maNguoiDung}."}

        # Kiểm tra vai trò
        if result[0].lower() == "admin":
            cursor.close()
            conn.close()
            return {"vaiTro": "Admin"}
        else:
            cursor.close()
            conn.close()
            return {"vaiTro": "Khách hàng"}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}

@app.post("/themThuongHieu")
def them_thuong_hieu(
    maThuongHieu: int,
    tenThuongHieu: str,
    moTa: str,
    ngayTao: str
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem thương hiệu đã tồn tại chưa
        check_sql = "SELECT maThuongHieu FROM THUONG_HIEU WHERE maThuongHieu = %s"
        cursor.execute(check_sql, (maThuongHieu,))
        result = cursor.fetchone()

        if result:
            # Nếu thương hiệu đã tồn tại
            cursor.close()
            conn.close()
            return {"message": f"Thương hiệu với mã {maThuongHieu} đã tồn tại."}

        # Thêm thương hiệu mới
        sql = """INSERT INTO THUONG_HIEU (maThuongHieu, tenThuongHieu, moTa, ngayTao) 
                 VALUES (%s, %s, %s, %s)"""
        adr = (maThuongHieu, tenThuongHieu, moTa, ngayTao)

        try:
            cursor.execute(sql, adr)
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Thêm thương hiệu thành công."}
        except Error as e:
            conn.close()
            return {"message": "Thêm thương hiệu thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.delete("/xoaThuongHieu")
def xoa_thuong_hieu(
    maThuongHieu: int  # Mã thương hiệu cần xóa
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem thương hiệu có tồn tại không
        check_sql = "SELECT maThuongHieu FROM THUONG_HIEU WHERE maThuongHieu = %s"
        cursor.execute(check_sql, (maThuongHieu,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy thương hiệu
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy thương hiệu với mã {maThuongHieu}."}

        # Xóa thương hiệu
        delete_sql = "DELETE FROM THUONG_HIEU WHERE maThuongHieu = %s"
        try:
            cursor.execute(delete_sql, (maThuongHieu,))
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Xóa thương hiệu thành công."}
        except Error as e:
            conn.close()
            return {"message": "Xóa thương hiệu thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}

@app.put("/suaThuongHieu")
def sua_thuong_hieu(
    maThuongHieu: int,  # Mã thương hiệu cần sửa
    tenThuongHieu: str = None,  # Tên thương hiệu mới (tùy chọn)
    moTa: str = None,  # Mô tả mới (tùy chọn)
    ngayTao: str = None  # Ngày tạo mới (tùy chọn)
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem thương hiệu có tồn tại không
        check_sql = "SELECT maThuongHieu FROM THUONG_HIEU WHERE maThuongHieu = %s"
        cursor.execute(check_sql, (maThuongHieu,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy thương hiệu
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy thương hiệu với mã {maThuongHieu}."}

        # Cập nhật thông tin thương hiệu
        update_sql = "UPDATE THUONG_HIEU SET "
        updates = []
        params = []

        if tenThuongHieu:
            updates.append("tenThuongHieu = %s")
            params.append(tenThuongHieu)
        if moTa:
            updates.append("moTa = %s")
            params.append(moTa)
        if ngayTao:
            updates.append("ngayTao = %s")
            params.append(ngayTao)

        if updates:
            update_sql += ", ".join(updates) + " WHERE maThuongHieu = %s"
            params.append(maThuongHieu)

            try:
                cursor.execute(update_sql, tuple(params))
                conn.commit()
                cursor.close()
                conn.close()
                return {"message": "Sửa thương hiệu thành công."}
            except Error as e:
                conn.close()
                return {"message": "Sửa thương hiệu thất bại: " + str(e)}
        else:
            cursor.close()
            conn.close()
            return {"message": "Không có thông tin nào để cập nhật."}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.get("/getThuongHieu")
def get_thuong_hieu(
    maThuongHieu: int  # Mã thương hiệu cần lấy thông tin
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor(dictionary=True)
        
        # Câu lệnh SQL để lấy thông tin thương hiệu
        sql = "SELECT * FROM THUONG_HIEU WHERE maThuongHieu = %s"
        cursor.execute(sql, (maThuongHieu,))
        result = cursor.fetchone()

        # Đóng con trỏ và kết nối
        cursor.close()
        conn.close()

        if result:
            # Nếu tìm thấy thương hiệu, trả về thông tin
            return result
        else:
            # Nếu không tìm thấy thương hiệu
            return {"message": f"Không tìm thấy thương hiệu với mã {maThuongHieu}."}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.post("/themDanhMuc")
def them_danh_muc(
    maDanhMuc: int,
    tenDanhMuc: str,
    ngayTao: str
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem danh mục đã tồn tại chưa
        check_sql = "SELECT maDanhMuc FROM DANH_MUC WHERE maDanhMuc = %s"
        cursor.execute(check_sql, (maDanhMuc,))
        result = cursor.fetchone()

        if result:
            # Nếu danh mục đã tồn tại
            cursor.close()
            conn.close()
            return {"message": f"Danh mục với mã {maDanhMuc} đã tồn tại."}

        # Thêm danh mục mới
        sql = """INSERT INTO DANH_MUC (maDanhMuc, tenDanhMuc, ngayTao) 
                 VALUES (%s, %s, %s)"""
        adr = (maDanhMuc, tenDanhMuc, ngayTao)

        try:
            cursor.execute(sql, adr)
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Thêm danh mục thành công."}
        except Error as e:
            conn.close()
            return {"message": "Thêm danh mục thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.delete("/xoaDanhMuc")
def xoa_danh_muc(
    maDanhMuc: int  # Mã danh mục cần xóa
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem danh mục có tồn tại không
        check_sql = "SELECT maDanhMuc FROM DANH_MUC WHERE maDanhMuc = %s"
        cursor.execute(check_sql, (maDanhMuc,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy danh mục
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy danh mục với mã {maDanhMuc}."}

        # Xóa danh mục
        delete_sql = "DELETE FROM DANH_MUC WHERE maDanhMuc = %s"
        try:
            cursor.execute(delete_sql, (maDanhMuc,))
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Xóa danh mục thành công."}
        except Error as e:
            conn.close()
            return {"message": "Xóa danh mục thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.put("/suaDanhMuc")
def sua_danh_muc(
    maDanhMuc: int,  # Mã danh mục cần sửa
    tenDanhMuc: str = None,  # Tên danh mục mới (tùy chọn)
    ngayTao: str = None  # Ngày tạo mới (tùy chọn)
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem danh mục có tồn tại không
        check_sql = "SELECT maDanhMuc FROM DANH_MUC WHERE maDanhMuc = %s"
        cursor.execute(check_sql, (maDanhMuc,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy danh mục
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy danh mục với mã {maDanhMuc}."}

        # Cập nhật thông tin danh mục
        update_sql = "UPDATE DANH_MUC SET "
        updates = []
        params = []

        if tenDanhMuc:
            updates.append("tenDanhMuc = %s")
            params.append(tenDanhMuc)
        if ngayTao:
            updates.append("ngayTao = %s")
            params.append(ngayTao)

        if updates:
            update_sql += ", ".join(updates) + " WHERE maDanhMuc = %s"
            params.append(maDanhMuc)

            try:
                cursor.execute(update_sql, tuple(params))
                conn.commit()
                cursor.close()
                conn.close()
                return {"message": "Sửa danh mục thành công."}
            except Error as e:
                conn.close()
                return {"message": "Sửa danh mục thất bại: " + str(e)}
        else:
            cursor.close()
            conn.close()
            return {"message": "Không có thông tin nào để cập nhật."}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.get("/getDanhMuc")
def get_danh_muc(
    maDanhMuc: int  # Mã danh mục cần lấy thông tin
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor(dictionary=True)
        
        # Câu lệnh SQL để lấy thông tin danh mục
        sql = "SELECT * FROM DANH_MUC WHERE maDanhMuc = %s"
        cursor.execute(sql, (maDanhMuc,))
        result = cursor.fetchone()

        # Đóng con trỏ và kết nối
        cursor.close()
        conn.close()

        if result:
            # Nếu tìm thấy danh mục, trả về thông tin
            return result
        else:
            # Nếu không tìm thấy danh mục
            return {"message": f"Không tìm thấy danh mục với mã {maDanhMuc}."}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    

@app.delete("/xoaSanPham")
def xoa_san_pham(
    maSanPham: int  # Mã sản phẩm cần xóa
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem sản phẩm có tồn tại không
        check_sql = "SELECT maSanPham FROM SAN_PHAM WHERE maSanPham = %s"
        cursor.execute(check_sql, (maSanPham,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy sản phẩm
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy sản phẩm với mã {maSanPham}."}

        # Xóa sản phẩm
        delete_sql = "DELETE FROM SAN_PHAM WHERE maSanPham = %s"
        try:
            cursor.execute(delete_sql, (maSanPham,))
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Xóa sản phẩm thành công."}
        except Error as e:
            conn.close()
            return {"message": "Xóa sản phẩm thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.put("/suaSanPham")
def sua_san_pham(
    maSanPham: int,  # Mã sản phẩm cần sửa
    tenSanPham: str = None,  # Tên sản phẩm mới (tùy chọn)
    moTa: str = None,  # Mô tả mới (tùy chọn)
    gia: float = None,  # Giá mới (tùy chọn)
    maDanhMuc: int = None,  # Mã danh mục mới (tùy chọn)
    maThuongHieu: int = None,  # Mã thương hiệu mới (tùy chọn)
    anhSanPham: str = None,  # Ảnh sản phẩm mới (tùy chọn)
    ngayCapNhat: str = None  # Ngày cập nhật mới (tùy chọn)
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem sản phẩm có tồn tại không
        check_sql = "SELECT maSanPham FROM SAN_PHAM WHERE maSanPham = %s"
        cursor.execute(check_sql, (maSanPham,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy sản phẩm
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy sản phẩm với mã {maSanPham}."}

        # Cập nhật thông tin sản phẩm
        update_sql = "UPDATE SAN_PHAM SET "
        updates = []
        params = []

        if tenSanPham:
            updates.append("tenSanPham = %s")
            params.append(tenSanPham)
        if moTa:
            updates.append("moTa = %s")
            params.append(moTa)
        if gia is not None:
            updates.append("gia = %s")
            params.append(gia)
        if maDanhMuc is not None:
            updates.append("maDanhMuc = %s")
            params.append(maDanhMuc)
        if maThuongHieu is not None:
            updates.append("maThuongHieu = %s")
            params.append(maThuongHieu)
        if anhSanPham:
            updates.append("anhSanPham = %s")
            params.append(anhSanPham)
        if ngayCapNhat:
            updates.append("ngayCapNhat = %s")
            params.append(ngayCapNhat)

        if updates:
            update_sql += ", ".join(updates) + " WHERE maSanPham = %s"
            params.append(maSanPham)

            try:
                cursor.execute(update_sql, tuple(params))
                conn.commit()
                cursor.close()
                conn.close()
                return {"message": "Sửa sản phẩm thành công."}
            except Error as e:
                conn.close()
                return {"message": "Sửa sản phẩm thất bại: " + str(e)}
        else:
            cursor.close()
            conn.close()
            return {"message": "Không có thông tin nào để cập nhật."}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}

@app.get("/getSanPham/{maSanPham}")
def get_san_pham(maSanPham: int):
    """
    Lấy thông tin sản phẩm dựa trên mã sản phẩm.

    Args:
        maSanPham (int): Mã sản phẩm.

    Returns:
        dict: Thông tin sản phẩm.
    """
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor(dictionary=True)
        
        # Lấy thông tin sản phẩm
        sql = "SELECT * FROM SAN_PHAM WHERE maSanPham = %s"
        cursor.execute(sql, (maSanPham,))
        result = cursor.fetchone()

        if result:
            cursor.close()
            conn.close()
            return result
        else:
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy sản phẩm với mã {maSanPham}."}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.post("/themBienTheSanPham")
def them_bien_the_san_pham(
    maSanPham: int,
    kichThuoc: str,
    soLuongTon: int
):
    # Kết nối đến cơ sở dữ liệu
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()

        # Kiểm tra xem sản phẩm đã tồn tại chưa
        check_sql = "SELECT maSanPham FROM SAN_PHAM WHERE maSanPham = %s"
        cursor.execute(check_sql, (maSanPham,))
        result = cursor.fetchone()

        if not result:
            # Nếu mã sản phẩm không tồn tại
            cursor.close()
            conn.close()
            return {"message": f"Sản phẩm với mã {maSanPham} không tồn tại."}

        # Thêm biến thể sản phẩm mới
        sql = """INSERT INTO BIEN_THE_SAN_PHAM (maSanPham, kichThuoc, soLuongTon) 
                 VALUES (%s, %s, %s)"""
        adr = (maSanPham, kichThuoc, soLuongTon)

        try:
            cursor.execute(sql, adr)
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Thêm biến thể sản phẩm thành công."}
        except Error as e:
            conn.close()
            return {"message": "Thêm biến thể sản phẩm thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}


@app.delete("/xoaBienTheSanPham")
def xoa_bien_the_san_pham(
    maBienThe: int  # Mã biến thể sản phẩm cần xóa
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem biến thể sản phẩm có tồn tại không
        check_sql = "SELECT maBienThe FROM BIEN_THE_SAN_PHAM WHERE maBienThe = %s"
        cursor.execute(check_sql, (maBienThe,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy biến thể sản phẩm
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy biến thể sản phẩm với mã {maBienThe}."}

        # Xóa biến thể sản phẩm
        delete_sql = "DELETE FROM BIEN_THE_SAN_PHAM WHERE maBienThe = %s"
        try:
            cursor.execute(delete_sql, (maBienThe,))
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Xóa biến thể sản phẩm thành công."}
        except Error as e:
            conn.close()
            return {"message": "Xóa biến thể sản phẩm thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.put("/suaBienTheSanPham")
def sua_bien_the_san_pham(
    maBienThe: int,  # Mã biến thể sản phẩm cần sửa
    maSanPham: int = None,  # Mã sản phẩm mới (tùy chọn)
    kichThuoc: str = None,  # Kích thước mới (tùy chọn)
    soLuongTon: int = None  # Số lượng tồn mới (tùy chọn)
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem biến thể sản phẩm có tồn tại không
        check_sql = "SELECT maBienThe FROM BIEN_THE_SAN_PHAM WHERE maBienThe = %s"
        cursor.execute(check_sql, (maBienThe,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy biến thể sản phẩm
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy biến thể sản phẩm với mã {maBienThe}."}

        # Cập nhật thông tin biến thể sản phẩm
        update_sql = "UPDATE BIEN_THE_SAN_PHAM SET "
        updates = []
        params = []

        if maSanPham is not None:
            updates.append("maSanPham = %s")
            params.append(maSanPham)
        if kichThuoc:
            updates.append("kichThuoc = %s")
            params.append(kichThuoc)
        if soLuongTon is not None:
            updates.append("soLuongTon = %s")
            params.append(soLuongTon)

        if updates:
            update_sql += ", ".join(updates) + " WHERE maBienThe = %s"
            params.append(maBienThe)

            try:
                cursor.execute(update_sql, tuple(params))
                conn.commit()
                cursor.close()
                conn.close()
                return {"message": "Sửa biến thể sản phẩm thành công."}
            except Error as e:
                conn.close()
                return {"message": "Sửa biến thể sản phẩm thất bại: " + str(e)}
        else:
            cursor.close()
            conn.close()
            return {"message": "Không có thông tin nào để cập nhật."}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.get("/getBienTheSanPham/{maBienThe}")
def get_bien_the_san_pham(maBienThe: int):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem biến thể sản phẩm có tồn tại không
        check_sql = "SELECT * FROM BIEN_THE_SAN_PHAM WHERE maBienThe = %s"
        cursor.execute(check_sql, (maBienThe,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy biến thể sản phẩm
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy biến thể sản phẩm với mã {maBienThe}."}

        # Trả về thông tin biến thể sản phẩm
        cursor.close()
        conn.close()
        return {"bienTheSanPham": result}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.post("/themDonHang")
def them_don_hang(
    maDonHang: int,
    maNguoiDung: int,
    diaChiGiaoHang: str,
    tongTien: float,
    trangThai: str,
    ngayTao: str
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem đơn hàng đã tồn tại chưa
        check_sql = "SELECT maDonHang FROM DON_HANG WHERE maDonHang = %s"
        cursor.execute(check_sql, (maDonHang,))
        result = cursor.fetchone()

        if result:
            # Nếu đơn hàng đã tồn tại
            cursor.close()
            conn.close()
            return {"message": f"Đơn hàng với mã {maDonHang} đã tồn tại."}

        # Thêm đơn hàng mới
        sql = """INSERT INTO DON_HANG (maDonHang, maNguoiDung, diaChiGiaoHang, tongTien, trangThai, ngayTao) 
                 VALUES (%s, %s, %s, %s, %s, %s)"""
        adr = (maDonHang, maNguoiDung, diaChiGiaoHang, tongTien, trangThai, ngayTao)

        try:
            cursor.execute(sql, adr)
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Thêm đơn hàng thành công."}
        except Error as e:
            conn.close()
            return {"message": "Thêm đơn hàng thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.delete("/xoaDonHang")
def xoa_don_hang(
    maDonHang: int  # Mã đơn hàng cần xóa
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem đơn hàng có tồn tại không
        check_sql = "SELECT maDonHang FROM DON_HANG WHERE maDonHang = %s"
        cursor.execute(check_sql, (maDonHang,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy đơn hàng
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy đơn hàng với mã {maDonHang}."}

        # Xóa đơn hàng
        delete_sql = "DELETE FROM DON_HANG WHERE maDonHang = %s"
        try:
            cursor.execute(delete_sql, (maDonHang,))
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Xóa đơn hàng thành công."}
        except Error as e:
            conn.close()
            return {"message": "Xóa đơn hàng thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.put("/suaDonHang")
def sua_don_hang(
    maDonHang: int,  # Mã đơn hàng cần sửa
    maNguoiDung: int = None,  # Mã người dùng mới (tùy chọn)
    diaChiGiaoHang: str = None,  # Địa chỉ giao hàng mới (tùy chọn)
    tongTien: float = None,  # Tổng tiền mới (tùy chọn)
    trangThai: str = None,  # Trạng thái mới (tùy chọn)
    ngayCapNhat: str = None  # Ngày cập nhật mới (tùy chọn)
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem đơn hàng có tồn tại không
        check_sql = "SELECT maDonHang FROM DON_HANG WHERE maDonHang = %s"
        cursor.execute(check_sql, (maDonHang,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy đơn hàng
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy đơn hàng với mã {maDonHang}."}

        # Cập nhật thông tin đơn hàng
        update_sql = "UPDATE DON_HANG SET "
        updates = []
        params = []

        if maNguoiDung is not None:
            updates.append("maNguoiDung = %s")
            params.append(maNguoiDung)
        if diaChiGiaoHang is not None:
            updates.append("diaChiGiaoHang = %s")
            params.append(diaChiGiaoHang)
        if tongTien is not None:
            updates.append("tongTien = %s")
            params.append(tongTien)
        if trangThai is not None:
            updates.append("trangThai = %s")
            params.append(trangThai)
        if ngayCapNhat is not None:
            updates.append("ngayCapNhat = %s")
            params.append(ngayCapNhat)

        if updates:
            update_sql += ", ".join(updates) + " WHERE maDonHang = %s"
            params.append(maDonHang)

            try:
                cursor.execute(update_sql, tuple(params))
                conn.commit()
                cursor.close()
                conn.close()
                return {"message": "Sửa đơn hàng thành công."}
            except Error as e:
                conn.close()
                return {"message": "Sửa đơn hàng thất bại: " + str(e)}
        else:
            cursor.close()
            conn.close()
            return {"message": "Không có thông tin nào để cập nhật."}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.get("/getDonHang/{maDonHang}")
def get_don_hang(maDonHang: int):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Lấy thông tin đơn hàng
        sql = "SELECT * FROM DON_HANG WHERE maDonHang = %s"
        cursor.execute(sql, (maDonHang,))
        result = cursor.fetchone()

        if result:
            cursor.close()
            conn.close()
            return {
                "maDonHang": result[0],
                "maNguoiDung": result[1],
                "diaChiGiaoHang": result[2],
                "tongTien": result[3],
                "trangThai": result[4],
                "ngayTao": result[5],
                "ngayCapNhat": result[6]
            }
        else:
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy đơn hàng với mã {maDonHang}."}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.post("/themChiTietDonHang")
def them_chi_tiet_don_hang(
    maChiTiet: int,
    maDonHang: int,
    maBienThe: int,
    soLuong: int,
    gia: float
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem chi tiết đơn hàng đã tồn tại chưa
        check_sql = "SELECT maChiTiet FROM CHI_TIET_DON_HANG WHERE maChiTiet = %s"
        cursor.execute(check_sql, (maChiTiet,))
        result = cursor.fetchone()

        if result:
            # Nếu chi tiết đơn hàng đã tồn tại
            cursor.close()
            conn.close()
            return {"message": f"Chi tiết đơn hàng với mã {maChiTiet} đã tồn tại."}

        # Thêm chi tiết đơn hàng mới
        sql = """INSERT INTO CHI_TIET_DON_HANG (maChiTiet, maDonHang, maBienThe, soLuong, gia) 
                 VALUES (%s, %s, %s, %s, %s)"""
        adr = (maChiTiet, maDonHang, maBienThe, soLuong, gia)

        try:
            cursor.execute(sql, adr)
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Thêm chi tiết đơn hàng thành công."}
        except Error as e:
            conn.close()
            return {"message": "Thêm chi tiết đơn hàng thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.delete("/xoaChiTietDonHang")
def xoa_chi_tiet_don_hang(
    maChiTiet: int  # Mã chi tiết đơn hàng cần xóa
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem chi tiết đơn hàng có tồn tại không
        check_sql = "SELECT maChiTiet FROM CHI_TIET_DON_HANG WHERE maChiTiet = %s"
        cursor.execute(check_sql, (maChiTiet,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy chi tiết đơn hàng
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy chi tiết đơn hàng với mã {maChiTiet}."}

        # Xóa chi tiết đơn hàng
        delete_sql = "DELETE FROM CHI_TIET_DON_HANG WHERE maChiTiet = %s"
        try:
            cursor.execute(delete_sql, (maChiTiet,))
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Xóa chi tiết đơn hàng thành công."}
        except Error as e:
            conn.close()
            return {"message": "Xóa chi tiết đơn hàng thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.put("/suaChiTietDonHang")
def sua_chi_tiet_don_hang(
    maChiTiet: int,  # Mã chi tiết đơn hàng cần sửa
    maDonHang: int = None,  # Mã đơn hàng mới (tùy chọn)
    maBienThe: int = None,  # Mã biến thể mới (tùy chọn)
    soLuong: int = None,  # Số lượng mới (tùy chọn)
    gia: float = None  # Giá mới (tùy chọn)
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem chi tiết đơn hàng có tồn tại không
        check_sql = "SELECT maChiTiet FROM CHI_TIET_DON_HANG WHERE maChiTiet = %s"
        cursor.execute(check_sql, (maChiTiet,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy chi tiết đơn hàng
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy chi tiết đơn hàng với mã {maChiTiet }."}

        # Cập nhật thông tin chi tiết đơn hàng
        update_sql = "UPDATE CHI_TIET_DON_HANG SET "
        updates = []
        params = []

        if maDonHang is not None:
            updates.append("maDonHang = %s")
            params.append(maDonHang)

        if maBienThe is not None:
            updates.append("maBienThe = %s")
            params.append(maBienThe)

        if soLuong is not None:
            updates.append("soLuong = %s")
            params.append(soLuong)

        if gia is not None:
            updates.append("gia = %s")
            params.append(gia)

        if updates:
            update_sql += ", ".join(updates) + " WHERE maChiTiet = %s"
            params.append(maChiTiet)

            try:
                cursor.execute(update_sql, tuple(params))
                conn.commit()
                cursor.close()
                conn.close()
                return {"message": "Sửa chi tiết đơn hàng thành công."}
            except Error as e:
                conn.close()
                return {"message": "Sửa chi tiết đơn hàng thất bại: " + str(e)}
        else:
            cursor.close()
            conn.close()
            return {"message": "Không có thông tin nào để cập nhật."}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.get("/getChiTietDonHang/{maChiTiet}")
def get_chi_tiet_don_hang(maChiTiet: int):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem chi tiết đơn hàng có tồn tại không
        check_sql = "SELECT * FROM CHI_TIET_DON_HANG WHERE maChiTiet = %s"
        cursor.execute(check_sql, (maChiTiet,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy chi tiết đơn hàng
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy chi tiết đơn hàng với mã {maChiTiet}."}

        # Trả về thông tin chi tiết đơn hàng
        cursor.close()
        conn.close()
        return {"chiTietDonHang": result}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.post("/themGioHang")
def them_gio_hang(
    maGioHang: int,
    maNguoiDung: int,
    maBienThe: int,
    soLuong: int,
    ngayTao: str
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem giỏ hàng đã tồn tại chưa
        check_sql = "SELECT maGioHang FROM GIO_HANG WHERE maGioHang = %s"
        cursor.execute(check_sql, (maGioHang,))
        result = cursor.fetchone()

        if result:
            # Nếu giỏ hàng đã tồn tại
            cursor.close()
            conn.close()
            return {"message": f"Giỏ hàng với mã {maGioHang} đã tồn tại."}

        # Thêm giỏ hàng mới
        sql = """INSERT INTO GIO_HANG (maGioHang, maNguoiDung, maBienThe, soLuong, ngayTao) 
                 VALUES (%s, %s, %s, %s, %s)"""
        adr = (maGioHang, maNguoiDung, maBienThe, soLuong, ngayTao)

        try:
            cursor.execute(sql, adr)
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Thêm giỏ hàng thành công."}
        except Error as e:
            conn.close()
            return {"message": "Thêm giỏ hàng thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.delete("/xoaGioHang")
def xoa_gio_hang(
    maGioHang: int  # Mã giỏ hàng cần xóa
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem giỏ hàng có tồn tại không
        check_sql = "SELECT maGioHang FROM GIO_HANG WHERE maGioHang = %s"
        cursor.execute(check_sql, (maGioHang,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy giỏ hàng
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy giỏ hàng với mã {maGioHang}."}

        # Xóa giỏ hàng
        delete_sql = "DELETE FROM GIO_HANG WHERE maGioHang = %s"
        try:
            cursor.execute(delete_sql, (maGioHang,))
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Xóa giỏ hàng thành công."}
        except Error as e:
            conn.close()
            return {"message": "Xóa giỏ hàng thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.put("/suaGioHang")
def sua_gio_hang(
    maGioHang: int,  # Mã giỏ hàng cần sửa
    maNguoiDung: int = None,  # Mã người dùng mới (tùy chọn)
    maBienThe: int = None,  # Mã biến thể mới (tùy chọn)
    soLuong: int = None,  # Số lượng mới (tùy chọn)
    ngayCapNhat: str = None  # Ngày cập nhật mới (tùy chọn)
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem giỏ hàng có tồn tại không
        check_sql = "SELECT maGioHang FROM GIO_HANG WHERE maGioHang = %s"
        cursor.execute(check_sql, (maGioHang,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy giỏ hàng
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy giỏ hàng với mã {maGioHang}."}

        # Cập nhật thông tin giỏ hàng
        update_sql = "UPDATE GIO_HANG SET "
        updates = []
        params = []

        if maNguoiDung is not None:
            updates.append("maNguoiDung = %s")
            params.append(maNguoiDung)

        if maBienThe is not None:
            updates.append("maBienThe = %s")
            params.append(maBienThe)

        if soLuong is not None:
            updates.append("soLuong = %s")
            params.append(soLuong)

        if ngayCapNhat is not None:
            updates.append("ngayTao = %s")
            params.append(ngayCapNhat)

        if updates:
            update_sql += ", ".join(updates) + " WHERE maGioHang = %s"
            params.append(maGioHang)

            try:
                cursor.execute(update_sql, tuple(params))
                conn.commit()
                cursor.close()
                conn.close()
                return {"message": "Sửa giỏ hàng thành công."}
            except Error as e:
                conn.close()
                return {"message": "Sửa giỏ hàng thất bại: " + str(e)}
        else:
            cursor.close()
            conn.close()
            return {"message": "Không có thông tin nào để cập nhật."}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.get("/getGioHang/{maGioHang}")
def get_gio_hang(maGioHang: int):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem giỏ hàng có tồn tại không
        check_sql = "SELECT * FROM GIO_HANG WHERE maGioHang = %s"
        cursor.execute(check_sql, (maGioHang,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy giỏ hàng
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy giỏ hàng với mã {maGioHang}."}

        # Trả về thông tin giỏ hàng
        cursor.close()
        conn.close()
        return {"gioHang": result}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.post("/themDanhSachYeuThich")
def them_danh_sach_yeu_thich(
    maYeuThich: int,
    maNguoiDung: int,
    maSanPham: int,
    ngayTao: str
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem danh sách yêu thích đã tồn tại chưa
        check_sql = "SELECT maYeuThich FROM DANH_SACH_YEU_THICH WHERE maYeuThich = %s"
        cursor.execute(check_sql, (maYeuThich,))
        result = cursor.fetchone()

        if result:
            # Nếu danh sách yêu thích đã tồn tại
            cursor.close()
            conn.close()
            return {"message": f"Danh sách yêu thích với mã {maYeuThich} đã tồn tại."}

        # Thêm danh sách yêu thích mới
        sql = """INSERT INTO DANH_SACH_YEU_THICH (maYeuThich, maNguoiDung, maSanPham, ngayTao) 
                 VALUES (%s, %s, %s, %s)"""
        adr = (maYeuThich, maNguoiDung, maSanPham, ngayTao)

        try:
            cursor.execute(sql, adr)
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Thêm danh sách yêu thích thành công."}
        except Error as e:
            conn.close()
            return {"message": "Thêm danh sách yêu thích thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.delete("/xoaDanhSachYeuThich")
def xoa_danh_sach_yeu_thich(
    maYeuThich: int  # Mã danh sách yêu thích cần xóa
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem danh sách yêu thích có tồn tại không
        check_sql = "SELECT maYeuThich FROM DANH_SACH_YEU_THICH WHERE maYeuThich = %s"
        cursor.execute(check_sql, (maYeuThich,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy danh sách yêu thích
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy danh sách yêu thích với mã {maYeuThich}."}

        # Xóa danh sách yêu thích
        delete_sql = "DELETE FROM DANH_SACH_YEU_THICH WHERE maYeuThich = %s"
        try:
            cursor.execute(delete_sql, (maYeuThich,))
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Xóa danh sách yêu thích thành công."}
        except Error as e:
            conn.close()
            return {"message": "Xóa danh sách yêu thích thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.put("/suaDanhSachYeuThich")
def sua_danh_sach_yeu_thich(
    maYeuThich: int,  # Mã danh sách yêu thích cần sửa
    maNguoiDung: int = None,  # Mã người dùng mới (tùy chọn)
    maSanPham: int = None,  # Mã sản phẩm mới (tùy chọn)
    ngayCapNhat: str = None  # Ngày cập nhật mới (tùy chọn)
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem danh sách yêu thích có tồn tại không
        check_sql = "SELECT maYeuThich FROM DANH_SACH_YEU_THICH WHERE maYeuThich = %s"
        cursor.execute(check_sql, (maYeuThich,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy danh sách yêu thích
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy danh sách yêu thích với mã {maYeuThich}."}

        # Cập nhật thông tin danh sách yêu thích
        update_sql = "UPDATE DANH_SACH_YEU_THICH SET "
        updates = []
        params = []

        if maNguoiDung is not None:
            updates.append("maNguoiDung = %s")
            params.append(maNguoiDung)

        if maSanPham is not None:
            updates.append("maSanPham = %s")
            params.append(maSanPham)

        if ngayCapNhat is not None:
            updates.append("ngayTao = %s")
            params.append(ngayCapNhat)

        if updates:
            update_sql += ", ".join(updates) + " WHERE maYeuThich = %s"
            params.append(maYeuThich)

            try:
                cursor.execute(update_sql, tuple(params))
                conn.commit()
                cursor.close()
                conn.close()
                return {"message": "Sửa danh sách yêu thích thành công."}
            except Error as e:
                conn.close()
                return {"message": "Sửa danh sách yêu thích thất bại: " + str(e)}
        else:
            cursor.close()
            conn.close()
            return {"message": "Không có thông tin nào để cập nhật."}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.get("/getDanhSachYeuThich/{maYeuThich}")
def get_danh_sach_yeu_thich(maYeuThich: int):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem danh sách yêu thích có tồn tại không
        check_sql = "SELECT * FROM DANH_SACH_YEU_THICH WHERE maYeuThich = %s"
        cursor.execute(check_sql, (maYeuThich,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy danh sách yêu thích
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy danh sách yêu thích với mã {maYeuThich}."}

        # Trả về thông tin danh sách yêu thích
        cursor.close()
        conn.close()
        return {"danhSachYeuThich": result}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.post("/themThanhToan")
def them_thanh_toan(
    maThanhToan: int,
    maDonHang: int,
    phuongThuc: str,
    trangThai: str,
    ngayThanhToan: str
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem thanh toán đã tồn tại chưa
        check_sql = "SELECT maThanhToan FROM THANH_TOAN WHERE maThanhToan = %s"
        cursor.execute(check_sql, (maThanhToan,))
        result = cursor.fetchone()

        if result:
            # Nếu thanh toán đã tồn tại
            cursor.close()
            conn.close()
            return {"message": f"Thanh toán với mã {maThanhToan} đã tồn tại."}

        # Thêm thanh toán mới
        sql = """INSERT INTO THANH_TOAN (maThanhToan, maDonHang, phuongThuc, trangThai, ngayThanhToan) 
                 VALUES (%s, %s, %s, %s, %s)"""
        adr = (maThanhToan, maDonHang, phuongThuc, trangThai, ngayThanhToan)

        try:
            cursor.execute(sql, adr)
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Thêm thanh toán thành công."}
        except Error as e:
            conn.close()
            return {"message": "Thêm thanh toán thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.delete("/xoaThanhToan")
def xoa_thanh_toan(
    maThanhToan: int  # Mã thanh toán cần xóa
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem thanh toán có tồn tại không
        check_sql = "SELECT maThanhToan FROM THANH_TOAN WHERE maThanhToan = %s"
        cursor.execute(check_sql, (maThanhToan,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy thanh toán
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy thanh toán với mã {maThanhToan}."}

        # Xóa thanh toán
        delete_sql = "DELETE FROM THANH_TOAN WHERE maThanhToan = %s"
        try:
            cursor.execute(delete_sql, (maThanhToan,))
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Xóa thanh toán thành công."}
        except Error as e:
            conn.close()
            return {"message": "Xóa thanh toán thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.put("/suaThanhToan")
def sua_thanh_toan(
    maThanhToan: int,  # Mã thanh toán cần sửa
    maDonHang: int = None,  # Mã đơn hàng mới (tùy chọn)
    phuongThuc: str = None,  # Phương thức mới (tùy chọn)
    trangThai: str = None,  # Trạng thái mới (tùy chọn)
    ngayCapNhat: str = None  # Ngày cập nhật mới (tùy chọn)
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem thanh toán có tồn tại không
        check_sql = "SELECT maThanhToan FROM THANH_TOAN WHERE maThanhToan = %s"
        cursor.execute(check_sql, (maThanhToan,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy thanh toán
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy thanh toán với mã {maThanhToan}."}

        # Cập nhật thông tin thanh toán
        update_sql = "UPDATE THANH_TOAN SET "
        updates = []
        params = []

        if maDonHang is not None:
            updates.append("maDonHang = %s")
            params.append(maDonHang)

        if phuongThuc is not None:
            updates.append("phuongThuc = %s")
            params.append(phuongThuc)

        if trangThai is not None:
            updates.append("trangThai = %s")
            params.append(trangThai)

        if ngayCapNhat is not None:
            updates.append("ngayThanhToan = %s")
            params.append(ngayCapNhat)

        if updates:
            update_sql += ", ".join(updates) + " WHERE maThanhToan = %s"
            params.append(maThanhToan)

            try:
                cursor.execute(update_sql, tuple(params))
                conn.commit()
                cursor.close()
                conn.close()
                return {"message": "Sửa thanh toán thành công."}
            except Error as e:
                conn.close()
                return {"message": "Sửa thanh toán thất bại: " + str(e)}
        else:
            cursor.close()
            conn.close()
            return {"message": "Không có thông tin nào để cập nhật."}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.get("/getThanhToan/{maThanhToan}")
def get_thanh_toan(maThanhToan: int):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem thanh toán có tồn tại không
        check_sql = "SELECT * FROM THANH_TOAN WHERE maThanhToan = %s"
        cursor.execute(check_sql, (maThanhToan,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy thanh toán
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy thanh toán với mã {maThanhToan}."}

        # Trả về thông tin thanh toán
        cursor.close()
        conn.close()
        return {"thanhToan": result}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.post("/themKichThuocSanPham")
def them_kich_thuoc_san_pham(
    maKichThuoc: str,
    maBienThe: int,
    kichThuoc: str,
    soLuongTon: int
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem kích thước sản phẩm đã tồn tại chưa
        check_sql = "SELECT maKichThuoc FROM KICH_THUOC_SAN_PHAM WHERE maKichThuoc = %s"
        cursor.execute(check_sql, (maKichThuoc,))
        result = cursor.fetchone()

        if result:
            # Nếu kích thước sản phẩm đã tồn tại
            cursor.close()
            conn.close()
            return {"message": f"Kích thước sản phẩm với mã {maKichThuoc} đã tồn tại."}

        # Thêm kích thước sản phẩm mới
        sql = """INSERT INTO KICH_THUOC_SAN_PHAM (maKichThuoc, maBienThe, kichThuoc, soLuongTon) 
                 VALUES (%s, %s, %s, %s)"""
        adr = (maKichThuoc, maBienThe, kichThuoc, soLuongTon)

        try:
            cursor.execute(sql, adr)
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Thêm kích thước sản phẩm thành công."}
        except Error as e:
            conn.close()
            return {"message": "Thêm kích thước sản phẩm thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.delete("/xoaKichThuocSanPham")
def xoa_kich_thuoc_san_pham(
    maKichThuoc: str  # Mã kích thước sản phẩm cần xóa
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem kích thước sản phẩm có tồn tại không
        check_sql = "SELECT maKichThuoc FROM KICH_THUOC_SAN_PHAM WHERE maKichThuoc = %s"
        cursor.execute(check_sql, (maKichThuoc,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy kích thước sản phẩm
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy kích thước sản phẩm với mã {maKichThuoc}."}

        # Xóa kích thước sản phẩm
        delete_sql = "DELETE FROM KICH_THUOC_SAN_PHAM WHERE maKichThuoc = %s"
        try:
            cursor.execute(delete_sql, (maKichThuoc,))
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Xóa kích thước sản phẩm thành công."}
        except Error as e:
            conn.close()
            return {"message": "Xóa kích thước sản phẩm thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.put("/suaKichThuocSanPham")
def sua_kich_thuoc_san_pham(
    maKichThuoc: str,  # Mã kích thước sản phẩm cần sửa
    maBienThe: int = None,  # Mã biến thể mới (tùy chọn)
    kichThuoc: str = None,  # Kích thước mới (tùy chọn)
    soLuongTon: int = None  # Số lượng tồn mới (tùy chọn)
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem kích thước sản phẩm có tồn tại không
        check_sql = "SELECT maKichThuoc FROM KICH_THUOC_SAN_PHAM WHERE maKichThuoc = %s"
        cursor.execute(check_sql, (maKichThuoc,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy kích thước sản phẩm
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy kích thước sản phẩm với mã {maKichThuoc}."}

        # Cập nhật thông tin kích thước sản phẩm
        update_sql = "UPDATE KICH_THUOC_SAN_PHAM SET "
        updates = []
        params = []

        if maBienThe is not None:
            updates.append("maBienThe = %s")
            params.append(maBienThe)

        if kichThuoc is not None:
            updates.append("kichThuoc = %s")
            params.append(kichThuoc)

        if soLuongTon is not None:
            updates.append("soLuongTon = %s")
            params.append(soLuongTon)

        # Thêm điều kiện WHERE
        update_sql += ", ".join(updates) + " WHERE maKichThuoc = %s"
        params.append(maKichThuoc)

        try:
            cursor.execute(update_sql, tuple(params))
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Sửa kích thước sản phẩm thành công."}
        except Error as e:
            conn.close()
            return {"message": "Sửa kích thước sản phẩm thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.get("/getKichThuocSanPham")
def get_kich_thuoc_san_pham(
    maKichThuoc: str  # Mã kích thước sản phẩm cần lấy
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Lấy thông tin kích thước sản phẩm
        select_sql = "SELECT * FROM KICH_THUOC_SAN_PHAM WHERE maKichThuoc = %s"
        cursor.execute(select_sql, (maKichThuoc,))
        result = cursor.fetchone()

        if result:
            cursor.close()
            conn.close()
            return {
                "maKichThuoc": result[0],
                "maBienThe": result[1],
                "kichThuoc": result[2],
                "soLuongTon": result[3]
            }
        else:
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy kích thước sản phẩm với mã {maKichThuoc}."}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}


@app.delete("/xoaAnhBienThe")
def xoa_anh_bien_the(
    maAnh: str  # Mã ảnh biến thể cần xóa
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem ảnh biến thể có tồn tại không
        check_sql = "SELECT maAnh FROM ANH_BIEN_THE WHERE maAnh = %s"
        cursor.execute(check_sql, (maAnh,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy ảnh biến thể
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy ảnh biến thể với mã {maAnh}."}

        # Xóa ảnh biến thể
        delete_sql = "DELETE FROM ANH_BIEN_THE WHERE maAnh = %s"
        try:
            cursor.execute(delete_sql, (maAnh,))
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Xóa ảnh biến thể thành công."}
        except Error as e:
            conn.close()
            return {"message": "Xóa ảnh biến thể thất bại: " + str(e)}
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.put("/suaAnhBienThe")
def sua_anh_bien_the(
    maAnh: str,  # Mã ảnh biến thể cần sửa
    maBienThe: int = None,  # Mã biến thể mới (tùy chọn)
    duongDan: str = None  # Đường dẫn mới (tùy chọn)
):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem ảnh biến thể có tồn tại không
        check_sql = "SELECT maAnh FROM ANH_BIEN_THE WHERE maAnh = %s"
        cursor.execute(check_sql, (maAnh,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy ảnh biến thể
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy ảnh biến thể với mã {maAnh}."}

        # Cập nhật thông tin ảnh biến thể
        update_sql = "UPDATE ANH_BIEN_THE SET "
        updates = []
        params = []

        if maBienThe is not None:
            updates.append("maBienThe = %s")
            params.append(maBienThe)

        if duongDan is not None:
            updates.append("duongDan = %s")
            params.append(duongDan)

        # Thêm điều kiện WHERE
        update_sql += ", ".join(updates) + " WHERE maAnh = %s"
        params.append(maAnh)

        try:
            cursor.execute(update_sql, tuple(params))
            conn.commit()
            cursor.close()
            conn.close()
            return {"message": "Sửa ảnh biến thể thành công."}
        except Error as e:
            conn.close()
            return {"message": "Sửa ảnh biến thể thất bại: " + str(e)}
    else:
        return {" message": "Lỗi kết nối cơ sở dữ liệu."}
    
@app.get("/getAnhBienThe/{maAnh}")
def get_anh_bien_the(maAnh: str):
    conn = db.connect_to_database()
    if not isinstance(conn, Error):
        cursor = conn.cursor()
        
        # Kiểm tra xem ảnh biến thể có tồn tại không
        check_sql = "SELECT * FROM ANH_BIEN_THE WHERE maAnh = %s"
        cursor.execute(check_sql, (maAnh,))
        result = cursor.fetchone()

        if not result:
            # Nếu không tìm thấy ảnh biến thể
            cursor.close()
            conn.close()
            return {"message": f"Không tìm thấy ảnh biến thể với mã {maAnh}."}

        # Trả về thông tin ảnh biến thể
        cursor.close()
        conn.close()
        return {
            "maAnh": result[0],
            "maBienThe": result[1],
            "duongDan": result[2]
        }
    else:
        return {"message": "Lỗi kết nối cơ sở dữ liệu."}
