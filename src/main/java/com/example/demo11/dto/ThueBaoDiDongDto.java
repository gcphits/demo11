package com.example.demo11.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThueBaoDiDongDto {
    private String isdn;
    private String ngayKichHoat;
    private String loaiThueBao;
    private String homeTinh;
    private String homeHuyen;
    private String homeXa;
    private String hangThueBao;
    private String goiCuoc;
    private String goiData;
    private String luuLuong;
    private String loaiMay;
    private String isSim4G;
}
