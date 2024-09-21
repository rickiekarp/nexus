
Send message to single client
```
curl -X POST --header "X-Recipient-Client: test" localhost:12000/v1/send
```


Broadcast message to all clients
```
curl -X POST -d "data" localhost:12000/v1/broadcast
```

Update preferences
```
curl -v -X PATCH localhost:12000/hub/v1/preferences -d '{"event":"preferences_changed","data": {"minClientVersion": "123"}}'
```

Concept:

1. 

{
  "seq": 1,
  "action": "authentication_challenge",
  "data": {
    "token": "sometoken"
  }
}


2. 

{
  "status": "OK",
  "seq_reply": 1
}


3. 

Notice seq_reply is 1, matching the seq of the original request. Using this a client can distinguish which request the response is meant for.

If there was any information to respond with, it would be encapsulated in a data field.

In the case of an error, the response would be:

{
  "status": "FAIL",
  "seq_reply": 2,
  "error": {
    "id": "some.error.id.here",
    "message": "Some error message here"
  }
}


4. 

{
  "event": "hello",
  "data": {
    "server_version": "3.6.0.1451.1c38da627ebb4e3635677db6939e9195"
  },
  "broadcast":{
    "omit_users": null,
    "user_id": "ay5sq51sebfh58ktrce5ijtcwy",
    "channel_id": "",
    "team_id": ""
  }
}