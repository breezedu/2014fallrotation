#Plot RPKM and ratio of DM436/DM435 over 10,000 bps region:


#read table for DM436_2L_10000 data;
ratiotable50 = read.table("D:/2014FallRotation/data/yulong/1010/Bin2RPKM/DM436_Bin2RPKM_2L_10000R.txt", sep="\t", col.names=c("chr_id", "start", "reads"))


#plot the figure for DM436 chromosome 2L only
plot((ratiotable50$reads), main="DM436, 10k RPKM ",cex=.3)



abline(h = 0, v = 0, col = "gray60")

ratiotable52 = read.table("D:/2014FallRotation/data/yulong/1010/Bin2RPKM/DM435_Bin2RPKM_2L_10000R.txt", sep="\t", col.names=c("chr_id", "start", "reads"))


points(ratiotable52$reads, main="DM435, 10k",cex=.3, col="red")

X11();

plot(filter(ratiotable52$reads *1000, rep(1,15)/15), main="DM435(black) vs DM436(red), 10k RPKM ",cex=.3)

points(filter(ratiotable50$reads*1000, rep(1,15)/15), main="DM436(red) vs DM435(black), 10k",cex=.3, col="red")
X11();

plot((ratiotable50$reads), main="DM436, 10k RPKM ",cex=.3)

X11(); 
plot(0, 0)