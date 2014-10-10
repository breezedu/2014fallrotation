#read_in data from D:/2014FallRotation/data/yulong/1006/ folder
#
#
# 1st, readin data of DM463/DM460

ratiotable3 = read.table("D:/2014FallRotation/data/yulong/1006/DM463OVERDM460_ratio_2L.txt", sep="\t", col.names=c("chr_id", "ratio", "region"))

plot(ratiotable3$ratio, main="DM463/DM460")

plot(x = ratiotable3$region*10000, y = ratiotable3$ratio, type = "l", main="DM463/DM460, SetYL/WT")

plot(x = ratiotable3$region*10000, y = ratiotable3$ratio, type = "l", xlim = c(0, 5E6), main="DM463/DM460, SetYL/WT")

plot(filter(ratiotable3$ratio,rep(1,5)), main="DM463/DM460")


###############################################################

# 2nd, readin data of DM462/DM460

ratiotable2 = read.table("D:/2014FallRotation/data/yulong/1006/DM462OVERDM460_ratio_2L.txt", sep="\t", col.names=c("chr_id", "ratio", "region"))

plot(ratiotable2$ratio, main="DM462/DM460")

plot(x = ratiotable2$region*10000, y = ratiotable2$ratio, type = "l", main="DM462/DM460, SetRS/WT")

plot(x = ratiotable2$region*10000, y = ratiotable2$ratio, type = "l", xlim = c(0, 5E6) , main="DM462/DM460, SetRS/WT")

plot(filter(ratiotable2$ratio,rep(1,5)), main="DM462/DM460")



###############################################################

# 3rd, readin data of DM461/DM460

ratiotable1 = read.table("D:/2014FallRotation/data/yulong/1006/DM461OVERDM460_ratio_2L.txt", sep="\t", col.names=c("chr_id", "ratio", "region"))

plot(ratiotable1$ratio, main="DM461/DM460")

plot(x = ratiotable1$region*10000, y = ratiotable1$ratio, type = "l", main="DM461/DM460, SetRS/WT")

plot(x = ratiotable1$region*10000, y = ratiotable1$ratio, type = "l", xlim = c(0, 5E6) , main="DM461/DM460, SetRS/WT")


plot(filter(ratiotable1$ratio,rep(1,5)), main="DM461/DM460")


##END group Two;
######################################
##
##
##
## for group One:

# 1st, readin data of DM459/DM456


ratiotable1_10k = read.table("D:/2014FallRotation/data/yulong/1006/DM459OVERDM456_ratio_2L.txt", sep="\t", col.names=c("chr_id", "ratio", "region"))

plot(ratiotable1_10k$ratio, main="DM459/DM456")

plot(x = ratiotable1_10k$region*10000, y = ratiotable1_10k$ratio, type = "l", main="DM459/DM456, SetYL/WT, 10000bp")

plot(x = ratiotable1_10k$region*10000, y = ratiotable1_10k$ratio, type = "l", xlim = c(0, 5E6) , main="DM459/DM456, SetYL/WT, 10000bp")


plot(filter(ratiotable2$ratio,rep(1,5)), main="DM458/DM456")




ratiotable1_50k = read.table("D:/2014FallRotation/data/yulong/1006/DM459OVERDM456_ratio_2L50000.txt", sep="\t", col.names=c("chr_id", "ratio", "region"))

plot(ratiotable1_50k$ratio, main="DM459/DM456")

plot(x = ratiotable1_50k$region*50000, y = ratiotable1_50k$ratio, type = "l", main="DM459/DM456, SetYL/WT, 50000bp")

plot(x = ratiotable1_50k$region*50000, y = ratiotable1_50k$ratio, type = "l", xlim = c(0, 5E6) , main="DM459/DM456, SetYL/WT, 50000bp")


plot(filter(ratiotable2$ratio,rep(1,5)), main="DM458/DM456")




###############################################################

# 2nd, readin data of DM458/DM456

ratiotable2_10k = read.table("D:/2014FallRotation/data/yulong/1006/DM458OVERDM456_ratio_2L.txt", sep="\t", col.names=c("chr_id", "ratio", "region"))

plot(ratiotable2_10k$ratio, main="DM458/DM456")

plot(x = ratiotable2_10k$region*10000, y = ratiotable2_10k$ratio, type = "l", main="DM458/DM456, SetRS/WT, 10000bp")

plot(x = ratiotable2_10k$region*10000, y = ratiotable2_10k$ratio, type = "l", xlim = c(0, 5E6) , main="DM458/DM456, SetRS/WT, 10000bp")


plot(filter(ratiotable2$ratio,rep(1,5)), main="DM458/DM456")


# 2nd, readin data of DM458/DM456

ratiotable2_50k = read.table("D:/2014FallRotation/data/yulong/1006/DM458OVERDM456_ratio_2L50000.txt", sep="\t", col.names=c("chr_id", "ratio", "region"))

plot(ratiotable2_50k$ratio, main="DM458/DM456")

plot(x = ratiotable2_50k$region*50000, y = ratiotable2_50k$ratio, type = "l", main="DM458/DM456, SetRS/WT, 50000bp")

plot(x = ratiotable1$region*50000, y = ratiotable2_50k$ratio, type = "l", xlim = c(0, 5E6) , main="DM458/DM456, SetRS/WT, 50000bp")



plot(filter(ratiotable2_50k$ratio,rep(1,5)), main="DM458/DM456")


###############################################################

# 3rd, readin data of DM457/DM456

ratiotable3_50k = read.table("D:/2014FallRotation/data/yulong/1006/DM457OVERDM456_ratio_2L50000.txt", sep="\t", col.names=c("chr_id", "ratio", "region"))

plot(ratiotable3_50k$ratio, main="DM457/DM456")

plot(x = ratiotable3_50k$region*50000, y = ratiotable3_50k$ratio, type = "l", main="DM457/DM456, pUC/WT, 50000bp")

plot(x = ratiotable3_50k$region*50000, y = ratiotable3_50k$ratio, type = "l", xlim = c(0, 5E6) , main="DM457/DM456, pUC/WT, 50000bp")


plot(filter(ratiotable1$ratio,rep(1,5)), main="DM457/DM456")



# 3rd, readin data of DM457/DM456

ratiotable10 = read.table("D:/2014FallRotation/data/yulong/1006/DM457OVERDM456_ratio_2L.txt", sep="\t", col.names=c("chr_id", "ratio", "region"))

plot(ratiotable10$ratio, main="DM457/DM456, 10")

plot(x = ratiotable10$region*10000, y = ratiotable10$ratio, type = "l", main="DM457/DM456, SetRS/WT, 10000bp")

plot(x = ratiotable10$region*10000, y = ratiotable10$ratio, type = "l", xlim = c(0, 5E6) , main="DM457/DM456, SetRS/WT, 10000bp")


plot(filter(ratiotable1$ratio,rep(1,5)), main="DM457/DM456")

##END group Two;

