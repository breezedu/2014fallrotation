
#load the ames.RData from url;

load(url("http://s3.amazonaws.com/assets.datacamp.com/course/dasi/ames.RData"))

#show the head
head(ames)

#show the 1st 10 lines of the data
head(ames, 10)

#show the tails
tail(ames)



# The 'ames' data frame is already loaded into the workspace.
# Assign the variables:
area = ames$Gr.Liv.Area

summary(area)


hist(area)