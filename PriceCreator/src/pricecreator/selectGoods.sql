select 
    g.ID as Code, g.NAME as Name, 
    p.Name as Producer, 
    RTRIM(t.ABBR, '%') as Tax, 
    g.LASTPRICE6 as Price,
    g.LASTPRICE1 as PiceReserve,
    g.LASTPRICE1 as PriceRreserveOrder,
    g.QTYREST as Quantity,
    g.MORIONID as Code1,
    (select NVL(ID, 0) from FIRMS_TMP where OKPO=21642228) as Code2,
    (select NVL(ID, 0) from FIRMS_TMP where OKPO=21643699) as Code3,
    (select NVL(ID, 0) from FIRMS_TMP where OKPO=22946976) as Code4,
    (select NVL(ID, 0) from FIRMS_TMP where OKPO=25184975) as Code6,
    (select NVL(ID, 0) from FIRMS_TMP where OKPO=31816235) as Code7,
    (select NVL(ID, 0) from FIRMS_TMP where OKPO=21194014) as Code8,
    (select NVL(ID, 0) from FIRMS_TMP where OKPO=21947206) as Code9,
    (select NVL(ID, 0) from FIRMS_TMP where OKPO=13808034) as Code10,
    (select NVL(ID, 0) from FIRMS_TMP where OKPO=35431349) as Code11
from goods g 
    INNER JOIN producers p ON g.PRODUCERID=p.ID 
    INNER JOIN taxs t ON g.TAXID=t.ID
    where g.QTYREST<>0     
    order by g.ID    
