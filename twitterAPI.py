import json
import oauth2
import tweepy


twitter_base="https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=twitterapi&count=2"

        
def oauth_req(url, key, secret, http_method="GET", post_body="", http_headers=None):
    consumer = oauth2.Consumer(key='Ui7pqiei3Tpe1UpBp1gZFISVW', secret='P8A94JTzEtyo1cGigJhWHZXZ4Cj2hZYvemtmVT6CgovknJjQeL')
    token = oauth2.Token(key=key, secret=secret)
    client = oauth2.Client(consumer, token)
    resp, content = client.request( url, method=http_method, body=post_body, headers=http_headers )
    return json.loads(content)

def getNumPosts(company, d1, d2):
    months = ["May", "Apr", "Mar", "Feb", "Jan", "Dec", "Nov"]
    tweets = oauth_req('https://api.twitter.com/1.1/statuses/user_timeline.json?&count=200&trim_user=true&screen_name=' + company, '785911658191265792-KGoZG2wcH2WX6avscCMs1eecqXOeLKu', "vOhGGZO9eWJIwBmkOeVjyco4ylb0bYIYuyRhrx9adzkAp")
    i = d2 - d1 + 1
    ret = [0] * i
    emptys = 0
    last_id = tweets[len(tweets) - 1]["id"]
    for k in range(900):
        try: 
            last_id = tweets[len(tweets) - 1]["id"] - 1
        except:
            last_id -= (10 ** 14) * 5
        for t in tweets:
            date = t["created_at"].split(" ")
            if (date[1] == "Apr"):
                if (int(date[2]) < d1):
                    return ret
                if (int(date[2]) >= d1  and int(date[2]) <= d2):
                    ret[int(date[2]) - d1] += 1
            if (date[1] == "Mar"):
                return ret
                    
        tweets = oauth_req('https://api.twitter.com/1.1/statuses/user_timeline.json?&count=200&trim_user=true&screen_name=' + company + "&max_id=" + str(last_id), '785911658191265792-KGoZG2wcH2WX6avscCMs1eecqXOeLKu', "vOhGGZO9eWJIwBmkOeVjyco4ylb0bYIYuyRhrx9adzkAp")
    return ret


#FOOD

print ("WENDY'S")
print (getNumPosts("wendys", 2, 30))
print ("")
print ("DUNKIN DONUTS")
print (getNumPosts("dunkindonuts", 2, 30))
print ("")
"""
#ENTERTAINMENT
print "VIACOM"
print (getNumPosts("viacom", 2, 30))
print ""
print "DISNEY"
print (getNumPosts("disney", 2, 30))
print ""

#APPAREL
print "ADIDAS"
print (getNumPosts("adidas", 2, 30))
print ""
print "NIKE"
print (getNumPosts("nike", 2, 30))
print ""

def getSpecificPost(n):
    return oauth_req("https://api.twitter.com/1.1/statuses/show.json?&id=" + str(n), '785911658191265792-KGoZG2wcH2WX6avscCMs1eecqXOeLKu', "vOhGGZO9eWJIwBmkOeVjyco4ylb0bYIYuyRhrx9adzkAp")
    
#print getSpecificPost(1107719029043671043)
    """
# =================================
"""
auth = tweepy.OAuthHandler('Ui7pqiei3Tpe1UpBp1gZFISVW', 'P8A94JTzEtyo1cGigJhWHZXZ4Cj2hZYvemtmVT6CgovknJjQeL')
auth.set_access_token('785911658191265792-KGoZG2wcH2WX6avscCMs1eecqXOeLKu',"vOhGGZO9eWJIwBmkOeVjyco4ylb0bYIYuyRhrx9adzkAp")

api = tweepy.API(auth)
ret = 0
for i in range(1,900):
    print i
    public_tweets = api.user_timeline('adidas', page = i)
    for tweet in public_tweets:
        if (tweet.in_reply_to_status_id == None):
            print tweet.text
            print ""
            ret += 1
print ret
            """
