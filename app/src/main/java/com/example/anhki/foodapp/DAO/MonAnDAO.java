package com.example.anhki.foodapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.anhki.foodapp.DTO.MonAnDTO;
import com.example.anhki.foodapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class MonAnDAO {
    private final SQLiteDatabase database;

    public MonAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemMonAn(MonAnDTO monAnDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_MONAN_TENMONAN,monAnDTO.getTenMonAn());
        contentValues.put(CreateDatabase.TB_MONAN_GIATIEN,monAnDTO.getGiaTien());
        contentValues.put(CreateDatabase.TB_MONAN_MALOAI,monAnDTO.getMaLoai());
        contentValues.put(CreateDatabase.TB_MONAN_HINHANH, monAnDTO.getHinhAnh());

        long kiemtra = database.insert(CreateDatabase.TB_MONAN,null,contentValues);
        return kiemtra != 0;
    }

    public boolean XoaMonAn(int MaMonAn) {
        // Thực hiện xóa món ăn trong cơ sở dữ liệu theo MaMonAn
        int kiemtra = database.delete(CreateDatabase.TB_MONAN, CreateDatabase.TB_MONAN_MAMON + " = ?", new String[]{String.valueOf(MaMonAn)});

        // Nếu xóa thành công, `kiemtra` sẽ trả về số dòng bị ảnh hưởng (>= 1), nếu thất bại trả về 0
        return kiemtra != 0;
    }


    @SuppressLint("Recycle")
    public List<MonAnDTO> LayDanhSachMonAnTheoLoai(int maloai){
        List<MonAnDTO> monAnDTOs = new ArrayList<MonAnDTO>();

        String truyvan = "SELECT * FROM " + CreateDatabase.TB_MONAN + " WHERE " + CreateDatabase.TB_MONAN_MALOAI + " = '" + maloai + "' ";
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            MonAnDTO monAnDTO = new MonAnDTO();
            monAnDTO.setHinhAnh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_HINHANH)));
            monAnDTO.setTenMonAn(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));
            monAnDTO.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
            monAnDTO.setMaMonAn(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MAMON)));
            monAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MALOAI)));

            monAnDTOs.add(monAnDTO);
            cursor.moveToNext();
        }

        return monAnDTOs;
    }
}
