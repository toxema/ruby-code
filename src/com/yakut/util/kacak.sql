CREATE PROCEDURE KACAK (
    sicilbit varchar(18),
    sicilbas varchar(18),
    tarihbas date,
    tarihbit date,
    departman_id integer,
    grup_id integer,
    tur integer)
returns (
    ogrno varchar(24),
    ad varchar(30),
    soyad varchar(30),
    sinif varchar(60),
    grup varchar(60),
    dizi varchar(250))
as
declare variable starih date;
declare variable sorgu varchar(500);
declare variable toplam smallint;
begin

   sorgu= 'select p.sicil,p.ad,p.soyad,d.ad SINIF,g.ad Grup from Personel p
           left join Departman d on d.id=p.departman_id
           left join Grup g on g.id=p.grup_id ' ;
            sorgu=:sorgu||' where 1=1 ';
         if (departman_id<>0) then
            sorgu=:sorgu|| ' and (p.departman_id=' || :departman_id || ') ';
         if (grup_id<>0) then
            sorgu=:sorgu|| ' and (p.grup_id=' || :grup_id || ') ';

          sorgu=:sorgu|| ' order by d.id,p.sicil';

  for
   execute statement  sorgu
                        into  :ogrno,:ad, :soyad, :sinif,:grup
  do begin
        starih=tarihBas;
         DIZI='';
        while (starih<=tarihbit) do
        begin
          --  tarih=starih;

            execute statement 'select count(*) from Hareket h
            left join personel p on p.id=h.personel_id
            where p.sicil='''||:ogrno||''' and (h.tarih between  ''' || :starih || ' 00:00:01''   and '''|| :starih || ' 23:59:59'')
             '
            into :toplam;
            if(toplam=0) then
                DIZI=:DIZI||'D,';
            else
                DIZI=:DIZI||'G,';

            starih=starih+1;
        end

        suspend;

   end


 


end