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
'''
print "WENDY'S"
print (getNumPosts("wendys", 2, 30))
print ""
print "DUNKIN DONUTS"
print (getNumPosts("dunkindonuts", 2, 30))
print ""

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
'''
def getPostsByDay(company, start, end):
    if company == "WENDY'S":
        return [97, 44, 171, 42, 64, 51, 112, 68, 68, 135, 122, 45, 41, 111, 57, 55, 78, 76, 26, 28, 78, 34, 108, 72, 26, 19, 46, 42, 93][start : end + 1]
    elif company == "DUNKIN DONUTS":
        return [23, 19, 20, 24, 1, 4, 35, 19, 33, 17, 26, 0, 4, 34, 15, 48, 16, 24, 4, 2, 19, 20, 24, 43, 31, 13, 8, 21, 31][start : end + 1]
    elif company == "VIACOM":
        return [3, 7, 9, 6, 0, 0, 2, 2, 0, 6, 1, 0, 0, 1, 0, 3, 0, 2, 0, 0, 1, 7, 1, 0, 1, 0, 0, 48, 9][start : end + 1]
    elif company == "DISNEY":
        return [4, 2, 2, 4, 0, 1, 2, 1, 3, 34, 4, 4, 3, 3, 5, 6, 5, 12, 3, 0, 5, 4, 5, 6, 5, 3, 2, 6, 2][start : end + 1]
    elif company == "ADIDAS":
        return [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0][start : end + 1]
    elif company == "NIKE":
        return [5, 1, 2, 16, 0, 0, 2, 12, 6, 7, 3, 5, 6, 7, 4, 12, 6, 0, 6, 1, 3, 5, 5, 7, 6, 4, 2, 4, 3][start : end + 1]
    else:
        print("Invalid Company Input")
        return

"""
RESULTS 


WENDY'S
[97, 44, 171, 42, 64, 51, 112, 68, 68, 135, 122, 45, 41, 111, 57, 55, 78, 76, 26, 28, 78, 34, 108, 72, 26, 19, 46, 42, 93]

DUNKIN DONUTS
[23, 19, 20, 24, 1, 4, 35, 19, 33, 17, 26, 0, 4, 34, 15, 48, 16, 24, 4, 2, 19, 20, 24, 43, 31, 13, 8, 21, 31]

VIACOM
[3, 7, 9, 6, 0, 0, 2, 2, 0, 6, 1, 0, 0, 1, 0, 3, 0, 2, 0, 0, 1, 7, 1, 0, 1, 0, 0, 48, 9]

DISNEY
[4, 2, 2, 4, 0, 1, 2, 1, 3, 34, 4, 4, 3, 3, 5, 6, 5, 12, 3, 0, 5, 4, 5, 6, 5, 3, 2, 6, 2]

ADIDAS
[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0]

NIKE
[5, 1, 2, 16, 0, 0, 2, 12, 6, 7, 3, 5, 6, 7, 4, 12, 6, 0, 6, 1, 3, 5, 5, 7, 6, 4, 2, 4, 3]
"""

def getSpecificPost(n):
    return oauth_req("https://api.twitter.com/1.1/statuses/show.json?&id=" + str(n), '785911658191265792-KGoZG2wcH2WX6avscCMs1eecqXOeLKu', "vOhGGZO9eWJIwBmkOeVjyco4ylb0bYIYuyRhrx9adzkAp")
    
#print getSpecificPost(1107719029043671043)
    
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
