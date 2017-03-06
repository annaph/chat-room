### Description

Simple Chat room demo application that demonstrates how can Akka be used to implement a chatroom application.

Chat room is represented as an Actor with following states:
  - list of user references called `joinedUsers`
  - list of posts called `chatHistory`
	
Following message types are used:
  - `JoinChatroom` - contains actor reference that we can send updates to and user display name
  - `PostToChatroom` - contains message to post to the chat room and user display name posting the message
	
Chat room actor behaviour:
  - When actor receives `JoinChatroom` message it internally updates list of `joinedUsers` and sends back to sender (new user) chat history
  - When actor receives `PostToChatroom` message it internally updates chat history and notifies all chatroom joined users.


### Motivation
This demo application is created primarily for educational purpose and is not an original work. It is based on Chat room example from book:
  - *Learning Akka, Jason Goodwin, ISBN 978-1-78439-300-7*

		
### Licence
  **Free software**
