# 1st, ratio of DM457/DM456 over 10,000 bps region:

ratiotable10 = read.table("D:/2014FallRotation/data/yulong/1013/Bin2Ratio/DM457OVERDM456_ratio_2L_10000.txt", sep="\t", col.names=c("chr_id", "ratio", "region"))

plot(ratiotable10$ratio, main="DM457/DM456, 10")

plot(x = ratiotable10$region*10000, y = ratiotable10$ratio, type = "l", main="DM457/DM456, SetRS/WT, 10000bp")

plot(x = ratiotable10$region*10000, y = ratiotable10$ratio, type = "l", xlim = c(0, 5E6) , main="DM457/DM456, SetRS/WT, 10000bp")

abline(h = 0, v = 0, col = "gray60")

plot(filter(ratiotable10$ratio,rep(1,2.5)), main="DM457/DM456")

plot(filter(ratiotable10$ratio,rep(1,10)), main="DM457/DM456")



# 2nd, ratio of DM457/DM456 over 50,000 bps region:

ratiotable10 = read.table("D:/2014FallRotation/data/yulong/1013/Bin2Ratio/DM457OVERDM456_ratio_2L_50000.txt", sep="\t", col.names=c("chr_id", "ratio", "region"))

plot(ratiotable10$ratio, main="DM457/DM456, 10")

plot(x = ratiotable10$region*50000, y = ratiotable10$ratio, type = "l", main="DM457/DM456, SetRS/WT, 50000bp")

plot(x = ratiotable10$region*50000, y = ratiotable10$ratio, type = "l", xlim = c(0, 5E6) , main="DM457/DM456, SetRS/WT, 50000bp")


abline(h = 0, v = 0, col = "gray60")

plot(filter(ratiotable10$ratio,rep(1,2.5)), main="DM457/DM456")

plot(filter(ratiotable10$ratio,rep(1,10)), main="DM457/DM456")