library(googleway)
api_key <- 'AIzaSyCcitetioBSfDhoJj67rKWq6mdA2U5awiA' 
p <- google_places(search_string = "Attractions in Warsaw, Poland",
              key = api_key)
p$results$name




library(httr)
library(rjson)

res <- fromJSON(file = "E:\\Workspace\\Bosch\\004_Hacaton\\airports.json")
cit <- character(); ci <- 1
for(x in 1:length(res)){
  c <- res[[x]]$cities
  for (y in 1: length(c)){
    cit[ci] <- (c[[y]][1])
    ci <- ci + 1
  }
}

attractions_name <- array(dim=c(length(cit),20))
attractions_rating <- array(dim=c(length(cit),20))
for (x in 1:length(cit)){
  attract <- google_places(search_string = paste("Attractions in", cit[[x]]),
                key = api_key,radius = 20)$results$name
  attract_r <- as.character(google_places(search_string = paste("Attractions in", cit[[x]]),
                           key = api_key,radius = 20)$results$rating)
  for(y in 1:length(attract)){
    attractions_name[x,y] <- attract[y]
    attractions_rating[x,y] <- attract_r[y]
  }
}

res2 <- res
a <- 1
for(x in 1:length(res2)){
  for(y in 1:length(res2[[x]]$cities)){
    res2[[c(x,2,y)]]["attractions"] <- list(attractions_name[a,])
    res2[[c(x,2,y)]]["rating"] <- list(attractions_rating[a,])
    a <- a+1
  }
}

for(x in 1:length(res2)){
  dat <- content(GET(url=paste(addr,res[[x]]$country,sep="/"),accept_json()))
  res2[[x]]['prefix'] <- as.character(dat[[1]]$callingCodes)
  res2[[x]]['timeZone'] <- as.character(dat[[1]]$timezones)
  res2[[x]]['currency'] <- as.character((dat[[1]]$currencies)[[1]]$name)
  res2[[x]]['language'] <- list(as.character((dat[[1]]$languages)[[1]]$name))
}

exportJSOS <- toJSON(res2)
write(exportJSOS,"fullAirports_2.json")