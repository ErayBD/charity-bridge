package com.eraybd.project;

public class BagisModel {
    String BagisTur;
    String Bagis_Aciklama;

    Integer Product_ID;


    public BagisModel(String bagisTur, String bagis_Aciklama,Integer product_ID) {
        BagisTur = bagisTur;
        Bagis_Aciklama = bagis_Aciklama;
        Product_ID = product_ID;
    }

    public String getBagisTur() {
        return BagisTur;
    }

    public String getBagis_Aciklama() {
        return Bagis_Aciklama;
    }

    public Integer get_ID() {
        return Product_ID;
    }
}
