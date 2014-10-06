#read_in data from D:/2014FallRotation/data/yulong/1006/ folder
#
#
# 1st, readin data of DM463/DM460

ratiotable3 = read.table("D:/2014FallRotation/data/yulong/1006/DM463OVERDM460_ratio_2L.txt", sep="\t", col.names=c("chr_id", "ratio", "region"))

plot(ratiotable3$ratio, main="DM463/DM460")

plot(filter(ratiotable3$ratio,rep(1,5)), main="DM463/DM460")


###############################################################

# 2nd, readin data of DM462/DM460

ratiotable2 = read.table("D:/2014FallRotation/data/yulong/1006/DM462OVERDM460_ratio_2L.txt", sep="\t", col.names=c("chr_id", "ratio", "region"))

plot(ratiotable2$ratio, main="DM462/DM460")

plot(filter(ratiotable2$ratio,rep(1,5)), main="DM462/DM460")



###############################################################

# 3rd, readin data of DM461/DM460

ratiotable1 = read.table("D:/2014FallRotation/data/yulong/1006/DM461OVERDM460_ratio_2L.txt", sep="\t", col.names=c("chr_id", "ratio", "region"))

plot(ratiotable1$ratio, main="DM461/DM460")

plot(filter(ratiotable1$ratio,rep(1,5)), main="DM461/DM460")


##END group Two;
######################################
##
##
##
## for group One:

# 1st, readin data of DM459/DM456

ratiotable3 = read.table("D:/2014FallRotation/data/yulong/1006/DM459OVERDM456_ratio_2L.txt", sep="\t", col.names=c("chr_id", "ratio", "region"))

plot(ratiotable3$ratio, main="DM459/DM456")

plot(filter(ratiotable3$ratio,rep(1,5)), main="DM459/DM456")


###############################################################

# 2nd, readin data of DM458/DM456

ratiotable2 = read.table("D:/2014FallRotation/data/yulong/1006/DM458OVERDM456_ratio_2L.txt", sep="\t", col.names=c("chr_id", "ratio", "region"))

plot(ratiotable2$ratio, main="DM458/DM456")

plot(filter(ratiotable2$ratio,rep(1,5)), main="DM458/DM456")



###############################################################

# 3rd, readin data of DM461/DM460

ratiotable1 = read.table("D:/2014FallRotation/data/yulong/1006/DM457OVERDM456_ratio_2L.txt", sep="\t", col.names=c("chr_id", "ratio", "region"))

plot(ratiotable1$ratio, main="DM457/DM456")

plot(filter(ratiotable1$ratio,rep(1,5)), main="DM457/DM456")


##END group Two;

