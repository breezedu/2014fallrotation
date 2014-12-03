#Plot stretch and merge of all Kc late regions into one
#read in data from url: http://alchemy.duhs.duke.edu/~gd44/Yulong/Yulong_1109/


#routine = "D:/2014FallRotation/data/yulong/1112/RPKM2Wiggle/StretchRegionWithH2Av_cutoff0.05.txt"; 


#Read data:
ratiotable10 = read.table("http://alchemy.duhs.duke.edu/~gd44/Yulong/Yulong_1112/StretchRegionWithH2Av_cutoff0.5.txt", sep="\t", col.names=c("index", "reads"))

ratiotable05 = read.table("http://alchemy.duhs.duke.edu/~gd44/Yulong/Yulong_1112/StretchRegionWithH2Av_cutoff0.25.txt", sep="\t", col.names=c("index", "reads"))

ratiotable005 = read.table("http://alchemy.duhs.duke.edu/~gd44/Yulong/Yulong_1112/StretchRegionWithH2Av_cutoff0.05.txt", sep="\t", col.names=c("index", "reads"))


########################################################
# plot all three figures in one figure.
# plot part one:
########################################################
X11()
tiff(file="h2avandtiming.tif")       ## make a new pnd document;

par(mfrow = c(3, 1), mar = c(2, 4, 3, 2))

#plot the figure for cutoff=0.5 
title1 = ( "cutoff 1.0")
plot((ratiotable10$reads),type = "l", main= title1, cex=.3, xlim = c(0, 100))
par(new=T) 

#plot the figure for cutoff=1.0 
ratiotableTime0.5 = read.table("http://alchemy.duhs.duke.edu/~gd44/Yulong/Yulong_1203/StretchRegionWithTime_cutoff0.5.txt", sep="\t", col.names=c("index", "reads"))

title1 = ( "cutoff 0.0001")
plot((ratiotableTime0.5$reads),type = "l",ann=F, cex=.3, xaxt="n", yaxt="n", col = "blue", xlim=c(0, 100))
legend("topright", legend = c("timing", "H2Av"), lty = 1, lwd=2, col=c("blue", "black") )

#dev.off()



########################################################
# plot the second part
#plot the figure for cutoff=0.5 
########################################################
title2 = ( "cutoff 0.5")
plot((ratiotable05$reads),type = "l", main= title2, cex=.3)

par(new=T) 

#plot the figure for cutoff=1.0 
ratiotableTime0.25 = read.table("http://alchemy.duhs.duke.edu/~gd44/Yulong/Yulong_1203/StretchRegionWithTime_cutoff0.25.txt", sep="\t", col.names=c("index", "reads"))

title1 = ( "cutoff 0.25")
plot((ratiotableTime0.25$reads),type = "l",ann=F, cex=.3, xaxt="n", yaxt="n", col = "blue", xlim=c(0, 100))
legend("topright", legend = c("timing", "H2Av"), lty = 1, lwd=2, col=c("blue", "black") )




########################################################
# the third part
#plot the figure for cutoff=0.05
########################################################

title3 = ( "cutoff 0.05")
plot((ratiotable005$reads),type = "l", main= title3, cex=.3)

par(new=T) 

#plot the figure for cutoff=1.0 
ratiotableTime0.05 = read.table("http://alchemy.duhs.duke.edu/~gd44/Yulong/Yulong_1203/StretchRegionWithTime_cutoff0.05.txt", sep="\t", col.names=c("index", "reads"))

title1 = ( "cutoff 0.05")
plot((ratiotableTime0.05$reads),type = "l",ann=F, cex=.3, xaxt="n", yaxt="n", col = "blue", xlim=c(0, 100))
legend("topright", legend = c("timing", "H2Av"), lty = 1, lwd=2, col=c("blue", "black") )



#close the png figure plotting
dev.off()


#############################
#plot seperate figures:

X11()
#plot the figure for cutoff=1.0 
title1 = ( "cutoff 1.0")
plot((ratiotable10$reads),type = "l", main= title1, cex=.3)

X11()
#plot the figure for cutoff=0.5 
title2 = ( "cutoff 0.5")
plot((ratiotable05$reads),type = "l", main= title2, cex=.3)

X11()
#plot the figure for cutoff=0.05
title3 = ( "cutoff 0.05")
plot((ratiotable005$reads),type = "l", main= title3, cex=.3)

