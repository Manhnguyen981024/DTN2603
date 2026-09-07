package entity;

import java.util.Scanner;

import entity.enums.LoaiTaiLieu;

public class Bao extends TaiLieu {
    private String ngayPhatHanh;

    public Bao() {}

    public Bao(String maTaiLieu, String tenNhaXuatBan, int soBanPhatHanh,
               String ngayPhatHanh) {
        super(maTaiLieu, tenNhaXuatBan, soBanPhatHanh);
        this.ngayPhatHanh = ngayPhatHanh;
    }

    public String getNgayPhatHanh() {
        return ngayPhatHanh;
    }

    public void setNgayPhatHanh(String ngayPhatHanh) {
        this.ngayPhatHanh = ngayPhatHanh;
    }

    @Override
    public LoaiTaiLieu getLoai() {
        return LoaiTaiLieu.BAO;
    }

    @Override
    public void nhapThongTin(Scanner sc) {
        super.nhapThongTin(sc);
        System.out.print("Ngày phát hành (dd/mm/yyyy): ");
        ngayPhatHanh = sc.nextLine();
    }

    @Override
    public void hienThi() {
        System.out.println("== BÁO ==");
        super.hienThi();
        System.out.println("Ngày phát hành: " + ngayPhatHanh);
    }
}
