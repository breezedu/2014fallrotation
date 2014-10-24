#Plot RPKM and ratio of DM436/DM435 Chromosome 2R over 10,000 bps region:

doc_name1 = "DM436";
doc_name2 = "DM435";
chr = "3L"
routine = "http://alchemy.duhs.duke.edu/~gd44/Yulong/Yulong_1024/"; 


###read table for https://alchemy.duhs.duke.edu/gd44~/Yulong/ ***
###DM436_2R_10000 data, 10000 means the window-width
###ratiotable36 = read.table("D:/2014FallRotation/data/yulong/1010/Bin2RPKM/DM436_Bin2RPKM_2L_10000R.txt",
###				 sep="\t", col.names=c("chr_id", "start", "reads"))

#read from url:
doc1 = paste0(routine, doc_name1,"_Bin2RPKM_",chr, "_10000R.txt");
doc1;
ratiotable36 = read.table( doc1, sep="\t", col.names=c("chr_id", "start", "reads"))

#ratiotable36 = read.table("http://alchemy.duhs.duke.edu/~gd44/Yulong/Yulong_1024/DM436_Bin2RPKM_",
#				+"doc_name",+"_10000R.txt", sep="\t", col.names=c("chr_id", "start", "reads"))

X11()
par(mfrow = c(3, 1), mar = c(2, 4, 3, 2))

#plot the figure for DM436 chromosome 2R only (BLACK)
title1 = paste(doc_name1, chr, "10k RPKM")
plot((ratiotable36$reads), main= title1, cex=.3)


#add the baseline
abline(h = 0, v = 0, col = "blue")



##############################################
#read table for DM435_2R_10000 data, read from url:

doc2 = paste0(routine, doc_name2,"_Bin2RPKM_",chr, "_10000R.txt");
ratiotable35 = read.table( doc2, sep="\t", col.names=c("chr_id", "start", "reads"))

#ratiotable35 = read.table("http://alchemy.duhs.duke.edu/~gd44/Yulong/Yulong_1024/DM435_Bin2RPKM_2R_10000R.txt", 
#			sep="\t", col.names=c("chr_id", "start", "reads"))


#plot the figure for DM435 chromosome 2L only
title2 = paste(doc_name2, chr, "10k RPKM")
plot(ratiotable35$reads, main=title2,cex=.3)

abline(h = 0, v = 0, col = "blue")



####################################################################
#plot DM435_2R and DM436_2R together, red for DM436, black for DM435

#plot then points:

title_ratio = paste0("Chr_", chr, doc_name1, "(black) vs ", doc_name2, "(red), 10k RPKM");
plot(ratiotable36$reads *1000, main=title_ratio,cex=.3)

points(ratiotable35$reads*1000, main=title_ratio,cex=.3, col="red")


#create a new window Without filter;
#X11();
#plot(ratiotable36$reads *1000, main=paste(title_ratio, "10k RPKM"),cex=.3)
#points(ratiotable35$reads*1000, main=title_ratio,cex=.3, col="red")

###########
#create a new window with filters;

#plot DM435_2R and DM436_2R together, red for DM436, black for DM435

#plot then points:
#plot(filter(ratiotable36$reads *1000, rep(1,15)/15), main=paste(title_ratio, "with filter"),cex=.3)

#points(filter(ratiotable35$reads*1000, rep(1,15)/15), main=paste(title_ratio, "with filter",cex=.3, col="red")






#############################################
#plot log2 ratio of DM436 over DM435;
#
#create a new window
##############################################

#create a new window, partition to 3 parts
X11()
par(mfrow = c(3, 1), mar = c(2, 4, 3, 2))

title_log = paste("Chr_", chr, doc_name1, "log2 Ratio:", doc_name1, "over", doc_name2, "10k");

#plot the log2 ratio with filter:
plot(log2(filter(ratiotable36$reads,rep(1,15)/15))-log2(filter(ratiotable35$reads, rep(1,15/15))), 
		main=title_log,cex=.3)

#add base line
abline(h = 0, v = 0, col = "blue")


#plot the log2 ratio without filter:
plot((log2(ratiotable36$reads)-log2(ratiotable35$reads)), 
       ylim=c(-5,5), main=paste(title_log, "without filter"),cex=.3)

abline(h = 0, v = 0, col = "blue")

#plot just the +1 if log2 ratio of DM436 is greater than DM435
#           or -1 if log2 ratio of DM436 is less than DM435

plot((log2(ratiotable36$reads)-log2(ratiotable35$reads))/abs(log2(ratiotable36$reads)-log2(ratiotable35$reads)), 
       ylim=c(-5,5), main=title_log,cex=.3)

abline(h = 0, v = 0, col = "blue")


