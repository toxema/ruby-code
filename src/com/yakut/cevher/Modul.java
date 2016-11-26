package com.yakut.cevher;

import com.yakut.util.Database;
import javax.swing.JOptionPane;

/**
 *
 * @author yakut
 */
public class Modul extends javax.swing.JFrame {
            public Modul() {
                        initComponents();
            }

            @SuppressWarnings("unchecked")
            // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
            private void initComponents() {

                        jScrollPane1 = new javax.swing.JScrollPane();
                        jTextArea1 = new javax.swing.JTextArea();
                        jButton1 = new javax.swing.JButton();
                        jTextField1 = new javax.swing.JTextField();
                        jScrollPane2 = new javax.swing.JScrollPane();
                        jTextArea2 = new javax.swing.JTextArea();

                        jTextArea1.setColumns(20);
                        jTextArea1.setRows(5);
                        jTextArea1.setText("CREATE or alter PROCEDURE SPD_TARIHHAR (\n    ilk_sicil varchar(12),\n    son_sicil varchar(12),\n    ilk_tarih timestamp,\n    son_tarih timestamp,\n    depart varchar(5),\n    bol varchar(5),\n    gor varchar(5),\n    ucret varchar(5),\n    s_kodu varchar(5))\nreturns (\n    sicil_no varchar(12),\n    adi varchar(15),\n    soyadi varchar(20),\n    tarih date,\n    giris_saati time,\n    cikis_saati time,\n    aciklama varchar(100),\n    departman varchar(25),\n    bolum varchar(25),\n    gorev varchar(25),\n    ucret_turu varchar(25))\nas\ndeclare variable fld_hareket varchar(1);\ndeclare variable fld_hartarih timestamp;\ndeclare variable har_say integer;\ndeclare variable son_hareket varchar(1);\ndeclare variable son_hartarih date;\ndeclare variable son_giris time;\ndeclare variable son_cikis time;\ndeclare variable per_sicil varchar(12);\ndeclare variable bas_tar date;\ndeclare variable bit_tar date;\ndeclare variable tatil_adi varchar(50);\ndeclare variable tatil_top integer;\ndeclare variable bul smallint;\ndeclare variable post_vard varchar(5);\ndeclare variable izin_adi varchar(25);\ndeclare variable post_vard2 varchar(5);\ndeclare variable post_vard3 varchar(5);\ndeclare variable post_vard4 varchar(5);\ndeclare variable post_vard5 varchar(5);\ndeclare variable post_vard6 varchar(5);\ndeclare variable post_vard7 varchar(5);\ndeclare variable vard_durum varchar(5);\ndeclare variable sql_code varchar(250);\ndeclare variable giris_tar date;\ndeclare variable kart_gec date;\ndeclare variable cik_tar date;\ndeclare variable dept2 varchar(25);\ndeclare variable bol2 varchar(25);\ndeclare variable gor2 varchar(25);\ndeclare variable ucret2 varchar(25);\ndeclare variable gun_tarih timestamp;\ndeclare variable tarih_kont timestamp;\ndeclare variable adi2 varchar(15);\ndeclare variable soyadi2 varchar(20);\ndeclare variable har_sql varchar(250);\ndeclare variable kartbasmaz integer;\ndeclare variable isciktar date;\nbegin\n  HAR_SAY=0;\n  KARTBASMAZ=0 ;\n\n  SQL_CODE='';\n  if (:DEPART<>'') then\n  SQL_CODE=:SQL_CODE||'AND(PR.DEPARTMAN='''||:DEPART||''')';\n    if (:BOL<>'') then\n    SQL_CODE=SQL_CODE||'AND(PR.BOLUM='''||:BOL||''')';\n      if (:GOR<>'') then\n      SQL_CODE=SQL_CODE||'AND(PR.GOREVI='''||:GOR||''')';\n        if (:UCRET<>'') then\n        SQL_CODE=SQL_CODE||'AND(PR.UCRET_TURU='''||:UCRET||''')';\n  /* PERSONEL LİSTESİ */\n\n  if (CAST(ILK_TARIH AS TIME)=CAST('00:00:01' AS TIME)) then\n  BEGIN\n    GUN_TARIH=CAST((CAST(ILK_TARIH AS DATE)-1)||' '||CAST('20:00:00' AS TIME) AS TIMESTAMP);\n    TARIH_KONT=CAST((CAST(ILK_TARIH AS DATE)-1)||' '||CAST('23:59:59' AS TIME) AS TIMESTAMP);\n    HAR_SQL='((GH.TARIH BETWEEN '''||:GUN_TARIH||''' AND '''||:TARIH_KONT||''')AND(GH.HAREKET='''||'G'||'''))OR';\n  END\n    ELSE\n    HAR_SQL='';\n  FOR\n    execute statement 'SELECT PR.SICIL_NO, PR.ADI, PR.SOYADI, PR.KART_GEC_TAR, PR.ISE_GIR_TAR, PR.ISE_CIK_TAR, DP.DEPART_ADI, BL.BOLUM_ADI, GR.GOREV_ADI, UC.UCRET_ADI ,PR.KART_BASMAZ,ISE_CIK_TAR FROM PERSONEL PR'||\n    ' LEFT JOIN DEPARTMAN DP ON(DP.DEPART_KODU=PR.DEPARTMAN)'||\n    ' LEFT JOIN BOLUM BL ON(BL.BOLUM_KODU=PR.BOLUM)'||\n    ' LEFT JOIN GOREV GR ON(GR.GOREV_KODU=PR.GOREVI)'||\n    ' LEFT JOIN UCRET UC ON(UC.UCRET_KODU=PR.UCRET_TURU)'||\n    ' WHERE ((PR.SICIL_NO>='''||:ILK_SICIL||''')AND(PR.SICIL_NO<='''||:SON_SICIL||'''))'||\n    ' AND(PR.KAYITTURU='''||1||''')'||\n    ' AND  (PR.ISE_CIK_TAR>'''||CAST(:ILK_TARIH AS DATE)||''' OR PR.ISE_CIK_TAR='''||'30.12.1899'||''')  '||\n    'AND(PR.SIRKET_KODU='''||:S_KODU||''')'||\n    :SQL_CODE||\n    ' ORDER BY PR.SICIL_NO'\n     INTO :PER_SICIL, :ADI2, :SOYADI2, :KART_GEC, :GIRIS_TAR, :CIK_TAR,:DEPT2, :BOL2, :GOR2, :UCRET2,:kartbasmaz,:ISCIKTAR\n  DO\n    BEGIN\n      SON_HAREKET='';  GIRIS_SAATI=NULL; CIKIS_SAATI=NULL;\n      HAR_SAY=0;\n      /*((GH.TARIH BETWEEN :GUN_TARIH AND :TARIH_KONT)AND(GH.HAREKET='G'))OR*/\n      FOR\n        execute statement 'SELECT GH.TARIH, GH.HAREKET  FROM GUNLUK_HAREKET GH'||\n        ' WHERE (GH.SICIL_NO='''||:PER_SICIL||''')AND'||\n        ' ('||:HAR_SQL||\n        ' (GH.TARIH BETWEEN '''||:ILK_TARIH||''' AND '''||:SON_TARIH||'''))'||\n        ' ORDER BY GH.SICIL_NO, GH.TARIH'\n        INTO  :FLD_HARTARIH, :FLD_HAREKET\n      DO\n        BEGIN\n          HAR_SAY=HAR_SAY+1;\n          IF(HAR_SAY=1)THEN\n          BEGIN\n             BAS_TAR=CAST(ILK_TARIH AS DATE);\n             BIT_TAR=CAST(FLD_HARTARIH AS DATE)-1;\n             WHILE(BAS_TAR<=BIT_TAR)DO\n             BEGIN\n              BUL=0;\n              TARIH=:BAS_TAR;\n              SICIL_NO=:PER_SICIL;\n              GIRIS_SAATI=NULL;\n              CIKIS_SAATI=NULL;\n              ACIKLAMA='İZİNSİZ';\n\n              if(kartbasmaz=1) then\n                ACIKLAMA='';\n\n\n              IZIN_ADI='-1';\n               IF(((:GIRIS_TAR=CAST('30.12.1899' as DATE))OR(:GIRIS_TAR<=:BAS_TAR))AND\n               ((:CIK_TAR=CAST('30.12.1899' as DATE))OR(:CIK_TAR>:BAS_TAR)))THEN\n               BEGIN\n                  FOR\n                   SELECT PS.PAZARTESI1,PS.SALI1,PS.CARSAMBA1,PS.PERSEMBE1,PS.CUMA1,PS.CUMARTESI1,PS.PAZAR1 FROM POSTPERSON PP\n                   LEFT JOIN POSTA PS ON(PS.POSTA_KODU=PP.POSTA_KODU)\n                   WHERE (PP.SICIL_NO=:PER_SICIL)AND(:TARIH BETWEEN PP.TARIH AND PP.BIT_TAR)\n                   INTO :POST_VARD,:POST_VARD2,:POST_VARD3,:POST_VARD4,:POST_VARD5,:POST_VARD6,:POST_VARD7\n                  DO\n                   BEGIN\n                     VARD_DURUM=' ';\n                     if (EXTRACT(WEEKDAY FROM :TARIH)=0)THEN\n                     VARD_DURUM=:POST_VARD7;\n                     ELSE if (EXTRACT(WEEKDAY FROM :TARIH)=1)THEN\n                     VARD_DURUM=:POST_VARD;\n                     ELSE if (EXTRACT(WEEKDAY FROM :TARIH)=2)THEN\n                     VARD_DURUM=:POST_VARD2;\n                     ELSE if (EXTRACT(WEEKDAY FROM :TARIH)=3)THEN\n                     VARD_DURUM=:POST_VARD3;\n                     ELSE if (EXTRACT(WEEKDAY FROM :TARIH)=4)THEN\n                     VARD_DURUM=:POST_VARD4;\n                     ELSE if (EXTRACT(WEEKDAY FROM :TARIH)=5)THEN\n                     VARD_DURUM=:POST_VARD5;\n                     ELSE if (EXTRACT(WEEKDAY FROM :TARIH)=6)THEN\n                     VARD_DURUM=:POST_VARD6;\n\n                     if (((SUBSTRING(VARD_DURUM FROM 1 FOR 1)='C')OR(SUBSTRING(VARD_DURUM FROM 1 FOR 1)='H'))AND(BUL=0)) then\n                     BEGIN\n                       ACIKLAMA='HAFTA TATİLİ';\n                       BUL=1;\n                     END\n                   END\n\n\n                   IF(BUL=0)THEN\n                   BEGIN\n                     FOR\n                       SELECT TATIL_ADI,COUNT(*) AS TOPLAM FROM TATIL_TANIMLARI\n                       WHERE (TARIH=:TARIH)\n                       GROUP BY TATIL_ADI\n                       INTO :TATIL_ADI, :TATIL_TOP\n                     DO\n                       BEGIN\n                         if ((TATIL_TOP>0)AND(BUL=0)) then\n                         BEGIN\n                           ACIKLAMA=:TATIL_ADI;\n                           BUL=1;\n                         END\n                       END\n                   END\n\n              IF(BUL=0)THEN\n              BEGIN\n               FOR\n                SELECT IZ.IZIN_ADI FROM IZIN_GIRIS IG\n                LEFT JOIN IZIN IZ ON(IZ.IZIN_KODU=IG.IZIN_KODU)\n                WHERE (IG.SICIL_NO=:PER_SICIL)AND(:TARIH BETWEEN IG.PNT_BAS_TAR AND IG.PNT_BIT_TAR)\n                INTO :IZIN_ADI\n               DO\n                 BEGIN\n                   if ((IZIN_ADI<>'-1')AND(BUL=0)) then\n                   BEGIN\n                     ACIKLAMA=:IZIN_ADI;\n                     BUL=1;\n                   END\n                 END\n              END\n              ADI=:ADI2;\n              SOYADI=:SOYADI2;\n              DEPARTMAN=:DEPT2;\n              BOLUM=:BOL2;\n              GOREV=:GOR2;\n              UCRET_TURU=:UCRET2;\n              BAS_TAR=BAS_TAR+1;\n              SUSPEND;\n             END\n              ELSE\n              BAS_TAR=BAS_TAR+1;\n            END/*WHILE*/\n          TARIH=NULL;\n          END\n\n          if ((HAR_SAY>1)AND((CAST(FLD_HARTARIH AS DATE)-SON_HARTARIH)>1)) then\n          BEGIN\n             if (SON_HAREKET='G') then\n             BEGIN\n              CIKIS_SAATI=NULL;\n              ACIKLAMA='ÇIKIŞ HAREKETİ YOK';\n             END\n               ELSE\n               BEGIN\n                 GIRIS_SAATI=NULL;\n                 ACIKLAMA='GİRİŞ SAATİ YOK';\n               END\n             DEPARTMAN=:DEPT2;\n             BOLUM=:BOL2;\n             GOREV=:GOR2;\n             UCRET_TURU=:UCRET2;\n             ADI=:ADI2;\n             SOYADI=:SOYADI2;\n             SUSPEND;\n             BAS_TAR=SON_HARTARIH+1;\n             BIT_TAR=CAST(FLD_HARTARIH AS DATE)-1;\n\n             WHILE(BAS_TAR<=BIT_TAR)DO\n             BEGIN\n              SICIL_NO=:PER_SICIL;\n              TARIH=:BAS_TAR;\n              GIRIS_SAATI=NULL;\n              CIKIS_SAATI=NULL;\n              ACIKLAMA='İZİNSİZ';\n              if(kartbasmaz=1) then\n                ACIKLAMA='';\n              IZIN_ADI='-1';\n              BUL=0;\n               IF(((:GIRIS_TAR=CAST('30.12.1899' as DATE))OR(:GIRIS_TAR<=:BAS_TAR))AND\n               ((:CIK_TAR=CAST('30.12.1899' as DATE))OR(:CIK_TAR>:BAS_TAR)))THEN\n               BEGIN\n              FOR\n                   SELECT PS.PAZARTESI1,PS.SALI1,PS.CARSAMBA1,PS.PERSEMBE1,PS.CUMA1,PS.CUMARTESI1,PS.PAZAR1 FROM POSTPERSON PP\n                   LEFT JOIN POSTA PS ON(PS.POSTA_KODU=PP.POSTA_KODU)\n                   WHERE (PP.SICIL_NO=:PER_SICIL)AND(:TARIH BETWEEN PP.TARIH AND PP.BIT_TAR)\n                   INTO :POST_VARD,:POST_VARD2,:POST_VARD3,:POST_VARD4,:POST_VARD5,:POST_VARD6,:POST_VARD7\n                  DO\n                   BEGIN\n                     VARD_DURUM=' ';\n                     if (EXTRACT(WEEKDAY FROM :TARIH)=0)THEN\n                     VARD_DURUM=:POST_VARD7;\n                     ELSE if (EXTRACT(WEEKDAY FROM :TARIH)=1)THEN\n                     VARD_DURUM=:POST_VARD;\n                     ELSE if (EXTRACT(WEEKDAY FROM :TARIH)=2)THEN\n                     VARD_DURUM=:POST_VARD2;\n                     ELSE if (EXTRACT(WEEKDAY FROM :TARIH)=3)THEN\n                     VARD_DURUM=:POST_VARD3;\n                     ELSE if (EXTRACT(WEEKDAY FROM :TARIH)=4)THEN\n                     VARD_DURUM=:POST_VARD4;\n                     ELSE if (EXTRACT(WEEKDAY FROM :TARIH)=5)THEN\n                     VARD_DURUM=:POST_VARD5;\n                     ELSE if (EXTRACT(WEEKDAY FROM :TARIH)=6)THEN\n                     VARD_DURUM=:POST_VARD6;\n\n                     if (((SUBSTRING(VARD_DURUM FROM 1 FOR 1)='C')OR(SUBSTRING(VARD_DURUM FROM 1 FOR 1)='H'))AND(BUL=0)) then\n                     BEGIN\n                       ACIKLAMA='HAFTA TATİLİ';\n                       BUL=1;\n                     END\n                   END\n\n\n                   IF(BUL=0)THEN\n                   BEGIN\n                     FOR\n                       SELECT TATIL_ADI,COUNT(*) AS TOPLAM FROM TATIL_TANIMLARI\n                       WHERE (TARIH=:TARIH)\n                       GROUP BY TATIL_ADI\n                       INTO :TATIL_ADI, :TATIL_TOP\n                     DO\n                       BEGIN\n                         if ((TATIL_TOP>0)AND(BUL=0)) then\n                         BEGIN\n                           ACIKLAMA=:TATIL_ADI;\n                           BUL=1;\n                         END\n                       END\n                   END\n\n              IF(BUL=0)THEN\n              BEGIN\n               FOR\n                SELECT IZ.IZIN_ADI FROM IZIN_GIRIS IG\n                LEFT JOIN IZIN IZ ON(IZ.IZIN_KODU=IG.IZIN_KODU)\n                WHERE (IG.SICIL_NO=:PER_SICIL)AND(:TARIH BETWEEN IG.PNT_BAS_TAR AND IG.PNT_BIT_TAR)\n                INTO :IZIN_ADI\n               DO\n                 BEGIN\n                   if ((IZIN_ADI<>'-1')AND(BUL=0)) then\n                   BEGIN\n                     ACIKLAMA=:IZIN_ADI;\n                     BUL=1;\n                   END\n                 END\n              END\n              DEPARTMAN=:DEPT2;\n              BOLUM=:BOL2;\n              GOREV=:GOR2;\n              UCRET_TURU=:UCRET2;\n              ADI=:ADI2;\n              SOYADI=:SOYADI2;\n              BAS_TAR=BAS_TAR+1;\n              SUSPEND;\n             END\n              ELSE\n              BAS_TAR=BAS_TAR+1;\n            END/*WHILE*/\n          TARIH=NULL;\n          END\n            if (FLD_HAREKET='G') then\n            BEGIN\n              if (FLD_HAREKET=SON_HAREKET) then\n              BEGIN\n                GIRIS_SAATI=:SON_GIRIS;\n                CIKIS_SAATI=NULL;\n                ACIKLAMA='ÇIKIŞ HAREKETİ YOK';\n                TARIH=:SON_HARTARIH;\n                SICIL_NO=:PER_SICIL;\n                --ACIKLAMA='';\n                DEPARTMAN=:DEPT2;\n                BOLUM=:BOL2;\n                GOREV=:GOR2;\n                UCRET_TURU=:UCRET2;\n                ADI=:ADI2;\n                SOYADI=:SOYADI2;\n                SUSPEND;\n              END\n\n              GIRIS_SAATI=CAST(:FLD_HARTARIH AS TIME);\n              if (CAST(:FLD_HARTARIH AS TIME)>=CAST('20:00:00' AS TIME)) then\n                TARIH=CAST(:FLD_HARTARIH AS DATE)+1;\n                  ELSE\n                  TARIH=CAST(:FLD_HARTARIH AS DATE);\n\n              SICIL_NO=PER_SICIL;\n\n            END /* HAREKET GİRİŞ */\n\n                ELSE\n                BEGIN\n\n                  SICIL_NO=:PER_SICIL;\n                  CIKIS_SAATI=CAST(:FLD_HARTARIH AS TIME);\n                  if ((SON_HAREKET=FLD_HAREKET)OR(HAR_SAY=1)OR(TARIH IS NULL)) then\n                  BEGIN\n                    GIRIS_SAATI=NULL;\n                    TARIH=CAST(:FLD_HARTARIH AS DATE);\n                    ACIKLAMA='GİRİŞ HAREKETİ YOK';\n                    /*SUREM=NULL;*/\n                  END\n                    ELSE\n                    ACIKLAMA='';\n                     /*if (NOT(GIRIS_SAATI IS NULL)) then\n                     BEGIN\n                      if (CIKIS_SAATI<GIRIS_SAATI) then\n                      BEGIN\n                       SUREM=CAST(((CAST('23:59:59' AS TIME)-GIRIS_SAATI)+(CIKIS_SAATI))AS VARCHAR(20));\n                      END\n                        ELSE\n                        SUREM=CAST((CIKIS_SAATI-GIRIS_SAATI) AS VARCHAR(20));\n                     SURE=:SUREM;\n                     END */\n\n                  DEPARTMAN=:DEPT2;\n                  BOLUM=:BOL2;\n                  GOREV=:GOR2;\n                  UCRET_TURU=:UCRET2;\n                  ADI=:ADI2;\n                  SOYADI=:SOYADI2;\n                  if ((TARIH>=CAST(ILK_TARIH AS DATE))AND(TARIH<=CAST(SON_TARIH AS DATE))) then\n                  SUSPEND;\n/*                  TARIH=NULL;*/\n                END\n\n                SON_GIRIS=GIRIS_SAATI;\n                SON_CIKIS=CIKIS_SAATI;\n                SON_HAREKET=FLD_HAREKET;\n                SON_HARTARIH=TARIH;\n      END   /*FOR*/\n        if (SON_HAREKET='G') then\n        BEGIN\n          if (CAST(:FLD_HARTARIH AS TIME)>=CAST('20:00:00' AS TIME)) then\n          TARIH=CAST(:FLD_HARTARIH AS DATE)+1;\n            ELSE\n            TARIH=CAST(:FLD_HARTARIH AS DATE);\n          GIRIS_SAATI=CAST(:FLD_HARTARIH AS TIME);\n          CIKIS_SAATI=NULL;\n          ACIKLAMA='ÇIKIŞ HAREKETİ YOK';\n          DEPARTMAN=:DEPT2;\n          BOLUM=:BOL2;\n          GOREV=:GOR2;\n          UCRET_TURU=:UCRET2;\n          ADI=:ADI2;\n          SOYADI=:SOYADI2;\n          if ((TARIH>=CAST(ILK_TARIH AS DATE))AND(TARIH<=CAST(SON_TARIH AS DATE))) then\n          SUSPEND;\n        END\n          if (HAR_SAY=0) then\n          BAS_TAR=ILK_TARIH;\n           ELSE\n           BAS_TAR=TARIH+1;\n\n          BIT_TAR=CAST(SON_TARIH AS DATE);\n\n            WHILE(BAS_TAR<=BIT_TAR)DO\n            BEGIN\n              SICIL_NO=:PER_SICIL;\n              TARIH=:BAS_TAR;\n              GIRIS_SAATI=NULL;\n              CIKIS_SAATI=NULL;\n              ACIKLAMA='İZİNSİZ';\n              if(kartbasmaz=1) then\n                ACIKLAMA='';\n              IZIN_ADI='-1';\n              BUL=0;\n               IF(((:GIRIS_TAR=CAST('30.12.1899' as DATE))OR(:GIRIS_TAR<=:BAS_TAR))AND\n               ((:CIK_TAR=CAST('30.12.1899' as DATE))OR(:CIK_TAR>:BAS_TAR)))THEN\n               BEGIN\n                 FOR\n                   SELECT PS.PAZARTESI1,PS.SALI1,PS.CARSAMBA1,PS.PERSEMBE1,PS.CUMA1,PS.CUMARTESI1,PS.PAZAR1 FROM POSTPERSON PP\n                   LEFT JOIN POSTA PS ON(PS.POSTA_KODU=PP.POSTA_KODU)\n                   WHERE (PP.SICIL_NO=:PER_SICIL)AND(:TARIH BETWEEN PP.TARIH AND PP.BIT_TAR)\n                   INTO :POST_VARD,:POST_VARD2,:POST_VARD3,:POST_VARD4,:POST_VARD5,:POST_VARD6,:POST_VARD7\n                  DO\n                   BEGIN\n                     VARD_DURUM=' ';\n                     if (EXTRACT(WEEKDAY FROM :TARIH)=0)THEN\n                     VARD_DURUM=:POST_VARD7;\n                     ELSE if (EXTRACT(WEEKDAY FROM :TARIH)=1)THEN\n                     VARD_DURUM=:POST_VARD;\n                     ELSE if (EXTRACT(WEEKDAY FROM :TARIH)=2)THEN\n                     VARD_DURUM=:POST_VARD2;\n                     ELSE if (EXTRACT(WEEKDAY FROM :TARIH)=3)THEN\n                     VARD_DURUM=:POST_VARD3;\n                     ELSE if (EXTRACT(WEEKDAY FROM :TARIH)=4)THEN\n                     VARD_DURUM=:POST_VARD4;\n                     ELSE if (EXTRACT(WEEKDAY FROM :TARIH)=5)THEN\n                     VARD_DURUM=:POST_VARD5;\n                     ELSE if (EXTRACT(WEEKDAY FROM :TARIH)=6)THEN\n                     VARD_DURUM=:POST_VARD6;\n\n                     if (((SUBSTRING(VARD_DURUM FROM 1 FOR 1)='C')OR(SUBSTRING(VARD_DURUM FROM 1 FOR 1)='H'))AND(BUL=0)) then\n                     BEGIN\n                       ACIKLAMA='HAFTA TATİLİ';\n                       BUL=1;\n                     END\n                   END\n\n                   IF(BUL=0)THEN\n                   BEGIN\n                     FOR\n                       SELECT TATIL_ADI,COUNT(*) AS TOPLAM FROM TATIL_TANIMLARI\n                       WHERE (TARIH=:TARIH)\n                       GROUP BY TATIL_ADI\n                       INTO :TATIL_ADI, :TATIL_TOP\n                     DO\n                       BEGIN\n                         if ((TATIL_TOP>0)AND(BUL=0)) then\n                         BEGIN\n                           ACIKLAMA=:TATIL_ADI;\n                           BUL=1;\n                         END\n                       END\n                   END\n\n              IF(BUL=0)THEN\n              BEGIN\n               FOR\n                SELECT IZ.IZIN_ADI FROM IZIN_GIRIS IG\n                LEFT JOIN IZIN IZ ON(IZ.IZIN_KODU=IG.IZIN_KODU)\n                WHERE (IG.SICIL_NO=:PER_SICIL)AND(:TARIH BETWEEN IG.PNT_BAS_TAR AND IG.PNT_BIT_TAR)\n                INTO :IZIN_ADI\n               DO\n                 BEGIN\n                   if ((IZIN_ADI<>'-1')AND(BUL=0)) then\n                   BEGIN\n                     ACIKLAMA=:IZIN_ADI;\n                     BUL=1;\n                   END\n                 END\n              END\n              DEPARTMAN=:DEPT2;\n              BOLUM=:BOL2;\n              GOREV=:GOR2;\n              UCRET_TURU=:UCRET2;\n              ADI=:ADI2;\n              SOYADI=:SOYADI2;\n              BAS_TAR=BAS_TAR+1;\n              SUSPEND;\n            END\n             ELSE\n             BAS_TAR=BAS_TAR+1;\n            END/*WHILE*/\n    END\n\nend\n");
                        jScrollPane1.setViewportView(jTextArea1);

                        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                        jButton1.setText("Modul Yukle");
                        jButton1.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                jButton1ActionPerformed(evt);
                                    }
                        });

                        jTextField1.setText("c:\\\\Uzmanpdks\\\\data\\\\2014\\\\data.fdb");

                        jTextArea2.setColumns(20);
                        jTextArea2.setRows(5);
                        jScrollPane2.setViewportView(jTextArea2);

                        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                        getContentPane().setLayout(layout);
                        layout.setHorizontalGroup(
                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jScrollPane2)
                                                            .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(jButton1))))
                        );
                        layout.setVerticalGroup(
                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jButton1)
                                                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                                .addContainerGap())
                        );

                        pack();
            }// </editor-fold>//GEN-END:initComponents

            private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                        Database db = new Database(jTextField1.getText());
                        if (db.connect()) {
                                    if (db.execute(jTextArea1.getText())) {
                                                JOptionPane.showMessageDialog(null, "Modül Yüklendi");
                                    }
                                    db.end();
                        } else {
                                    jTextArea2.setText(db.getException().toString());
                        }
            }//GEN-LAST:event_jButton1ActionPerformed

            /**
             * @param args the command line arguments
             */
            public static void main(String args[]) {
                        /* Set the Nimbus look and feel */
                        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
                         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
                         */
                        try {
                                    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                                                if ("Nimbus".equals(info.getName())) {
                                                            javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                                            break;
                                                }
                                    }
                        } catch (ClassNotFoundException ex) {
                                    java.util.logging.Logger.getLogger(Modul.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        } catch (InstantiationException ex) {
                                    java.util.logging.Logger.getLogger(Modul.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                                    java.util.logging.Logger.getLogger(Modul.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                                    java.util.logging.Logger.getLogger(Modul.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                        //</editor-fold>

                        /* Create and display the form */
                        java.awt.EventQueue.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                                new Modul().setVisible(true);
                                    }
                        });
            }
            // Variables declaration - do not modify//GEN-BEGIN:variables
            private javax.swing.JButton jButton1;
            private javax.swing.JScrollPane jScrollPane1;
            private javax.swing.JScrollPane jScrollPane2;
            private javax.swing.JTextArea jTextArea1;
            private javax.swing.JTextArea jTextArea2;
            private javax.swing.JTextField jTextField1;
            // End of variables declaration//GEN-END:variables
}