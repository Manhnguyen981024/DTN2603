package entity.enums;

public enum LoaiTaiLieu {
    SACH("Sách"),
    TAP_CHI("Tạp chí"),
    BAO("Báo");

    private final String ten;

    LoaiTaiLieu(String ten) {
        this.ten = ten;
    }

    public String getTen() {
        return ten;
    }
}
