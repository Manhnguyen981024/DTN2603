package entity;

import java.util.Scanner;

import entity.enums.LoaiTaiLieu;

public abstract class TaiLieu {
    protected String maTaiLieu;
    protected String tenNhaXuatBan;
    protected int soBanPhatHanh;

    public TaiLieu() {}

    public TaiLieu(String maTaiLieu, String tenNhaXuatBan, int soBanPhatHanh) {
        this.maTaiLieu = maTaiLieu;
        this.tenNhaXuatBan = tenNhaXuatBan;
        this.soBanPhatHanh = soBanPhatHanh;
    }

    public String getMaTaiLieu() {
        return maTaiLieu;
    }

    public void setMaTaiLieu(String maTaiLieu) {
        this.maTaiLieu = maTaiLieu;
    }

    public String getTenNhaXuatBan() {
        return tenNhaXuatBan;
    }

    public void setTenNhaXuatBan(String tenNhaXuatBan) {
        this.tenNhaXuatBan = tenNhaXuatBan;
    }

    public int getSoBanPhatHanh() {
        return soBanPhatHanh;
    }

    public void setSoBanPhatHanh(int soBanPhatHanh) {
        this.soBanPhatHanh = soBanPhatHanh;
    }

    public abstract LoaiTaiLieu getLoai();

    public void nhapThongTin(Scanner sc) {
        System.out.print("Tên nhà xuất bản: ");
        tenNhaXuatBan = sc.nextLine();
        while (true) {
            System.out.print("Số bản phát hành: ");
            try {
                soBanPhatHanh = Integer.parseInt(sc.nextLine());
                if (soBanPhatHanh > 0) {
                    break;
                }
                System.out.println("Số bản phát hành phải lớn hơn 0!");
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số hợp lệ!");
            }
        }
    }

    public void hienThi() {
        System.out.println("Mã tài liệu: " + maTaiLieu);
        System.out.println("Tên nhà xuất bản: " + tenNhaXuatBan);
        System.out.println("Số bản phát hành: " + soBanPhatHanh);
    }
}
