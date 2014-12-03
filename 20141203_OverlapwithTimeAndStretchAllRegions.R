#Plot stretch and merge of all Kc late regions into one
#read in data from url: http://alchemy.duhs.duke.edu/~gd44/Yulong/Yulong_1117/


#local routine = "D:/2014FallRotation/data/yulong/1117/RPKM2Wiggle/StretchRegionWithTime_cutoff0.txt"; 


#Read data:
ratiotable10 = read.table("http://alchemy.duhs.duke.edu/~gd44/Yulong/Yulong_1203/StretchRegionWithTime_cutoff1.0E-4.txt", sep="\t", col.names=c("index", "reads"))

ratiotable05 = read.table("http://alchemy.duhs.duke.edu/~gd44/Yulong/Yulong_1203/StretchRegionWithTime_cutoff0.05.txt", sep="\t", col.names=c("index", "reads"))

ratiotable005 = read.table("http://alchemy.duhs.duke.edu/~gd44/Yulong/Yulong_1203/StretchRegionWithTime_cutoff0.0.txt", sep="\t", col.names=c("index", "reads"))


#######################
#plot all three figures in one figure.
X11()
par(mfrow = c(3, 1), mar = c(2, 4, 3, 2))

#plot the figure for cutoff=1.0 
title1 = ( "cutoff 0.0001")
plot((ratiotable10$reads),type = "l", main= title1, cex=.3)

#plot the figure for cutoff=0.5 
title2 = ( "cutoff 0.05")
plot((ratiotable05$reads),type = "l", main= title2, cex=.3)


#plot the figure for cutoff=0.05
title3 = ( "cutoff 0")
plot((ratiotable005$reads),type = "l", main= title3, cex=.3)


#############################
#plot seperate figures:

X11()
#plot the figure for cutoff=1.0 
title1 = ( "overlap of time & lateRegion cutoff 0.0001")
plot((ratiotable10$reads),type = "l", main= title1, cex=.3)

X11()
#plot the figure for cutoff=0.5 
title2 = ( "overlap of time & lateRegion cutoff 0.05")
plot((ratiotable05$reads),type = "l", main= title2, cex=.3)

X11()
#plot the figure for cutoff=0.05
title3 = ( "overlap of time & lateRegion cutoff 0")
plot((ratiotable005$reads),type = "l", main= title3, cex=.3)

