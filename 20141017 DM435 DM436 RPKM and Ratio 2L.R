#Plot RPKM and ratio of DM436/DM435 Chromosome 2L over 10,000 bps region:

par(mfrow = c(3, 1), mar = c(2, 4, 3, 2))


#read table for DM436_2L_10000 data, 10000 means the window-width
ratiotable36 = read.table("D:/2014FallRotation/data/yulong/1010/Bin2RPKM/DM436_Bin2RPKM_2L_10000R.txt", sep="\t", col.names=c("chr_id", "start", "reads"))


#plot the figure for DM436 chromosome 2L only (BLACK)
plot((ratiotable36$reads), main="DM436, 10k RPKM ",cex=.3)


#add the baseline
abline(h = 0, v = 0, col = "blue")



##############################################
#read table for DM435_2L_10000 data
ratiotable35 = read.table("D:/2014FallRotation/data/yulong/1010/Bin2RPKM/DM435_Bin2RPKM_2L_10000R.txt", sep="\t", col.names=c("chr_id", "start", "reads"))


#plot the figure for DM435 chromosome 2L only
plot(ratiotable35$reads, main="DM435, 10k",cex=.3)

abline(h = 0, v = 0, col = "blue")



##################
#plot DM435_2L and DM436_2L together, red for DM436, black for DM435

#plot then points:
plot(filter(ratiotable36$reads *1000, rep(1,15)/15), main="DM436(black) vs DM435(red), 10k RPKM ",cex=.3)

points(filter(ratiotable35$reads*1000, rep(1,15)/15), main="DM436(black) vs DM435(red), 10k",cex=.3, col="red")


#create a new window Without filter;
X11();
plot(ratiotable36$reads *1000, main="DM436(black) vs DM435(red), 10k RPKM ",cex=.3)

points(ratiotable35$reads*1000, main="DM436(black) vs DM435(red), 10k",cex=.3, col="red")

###########
#create a new window with filters;

#plot DM435_2L and DM436_2L together, red for DM436, black for DM435

#plot then points:
plot(filter(ratiotable36$reads *1000, rep(1,15)/15), main="DM436(black) vs DM435(red), 10k RPKM ",cex=.3)

points(filter(ratiotable35$reads*1000, rep(1,15)/15), main="DM436(black) vs DM435(red), 10k",cex=.3, col="red")




#create a new window
X11();

###########################################
#plot log2 ratio of DM436 over DM435;

plot(log2(filter(ratiotable36$reads,rep(1,15)/15))-log2(filter(ratiotable35$reads, rep(1,15/15))), main="log2 Ratio:DM436 over DM435, 10",cex=.3)

#add base line
abline(h = 0, v = 0, col = "blue")

X11()

> X11()
> par(mfrow = c(2, 1))
> plot(0, 0); plot(0, 0)
> par(mfrow = c(2, 1), mar = c(2, 4, 3, 2))
> plot(0, 0); plot(0, 0)
> 

X11(); 
plot(0, 0)