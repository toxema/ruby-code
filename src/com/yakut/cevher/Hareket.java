/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.cevher;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author yakut
 */
public class Hareket {

            String sicil;
            String kart;
            Date tarih;
            String posta;
            String ad;
            String sure;
            String kapi;
            String yon;
            boolean islem = false;

            public Hareket() {
            }

            public Hareket(String sicil, String kart, Date tarih, String posta) {
                        this.sicil = sicil;
                        this.kart = kart;
                        this.tarih = tarih;
                        this.posta = posta;
            }

            public String getYon() {
                        return yon;
            }

            public void setYon(String yon) {
                        this.yon = yon;
            }

            public String getKapi() {
                        return kapi;
            }

            public void setKapi(String kapi) {
                        this.kapi = kapi;
            }

            public String getSure() {
                        return sure;
            }

            public void setSure(String sure) {
                        this.sure = sure;
            }

            public String getAd() {
                        return ad;
            }

            public void setAd(String ad) {
                        this.ad = ad;
            }

            public Date getTarih() {
                        return tarih;
            }

            public void setTarih(Date tarih) {
                        this.tarih = tarih;
            }

            public boolean isIslem() {
                        return islem;
            }

            public void setIslem(boolean islem) {
                        this.islem = islem;
            }

            public String getSicil() {
                        return sicil;
            }

            public void setSicil(String sicil) {
                        this.sicil = sicil;
            }

            public String getKart() {
                        return kart;
            }

            public void setKart(String kart) {
                        this.kart = kart;
            }

            public String getPosta() {
                        return posta;
            }

            public void setPosta(String posta) {
                        this.posta = posta;
            }

            @Override
            public int hashCode() {
                        int hash = 5;
                        hash = 71 * hash + Objects.hashCode(this.sicil);
                        hash = 71 * hash + Objects.hashCode(this.tarih);
                        return hash;
            }

            @Override
            public boolean equals(Object obj) {
                        if (obj == null) {
                                    return false;
                        }
                        if (getClass() != obj.getClass()) {
                                    return false;
                        }
                        final Hareket other = (Hareket) obj;
                        if (!Objects.equals(this.sicil, other.sicil)) {
                                    return false;
                        }
                        if (!Objects.equals(this.tarih, other.tarih)) {
                                    return false;
                        }
                        return true;
            }
}
