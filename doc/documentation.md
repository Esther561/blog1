# Documentation
This file contains the documentation of page design, database design, etc.

## Project structure
```
.
|- .idea
|- lib
|- out
|- doc
|   |- documentation.md          [this file]
|   |- journal.md                [diary]
|   `- roadmap.md                [plans]
|- scripts
|   |- initSchema.sql            [create tables]
|   `- sampleData.sql            [generate sample data for testing]
|- src
|   `- ictgradschool.project
|       |- servlet
|       |   |- page              [servlets which directly dispatch to jsps]
|       |   |- action            [servlets which respond to a form action]
|       |   `- ajax              [servlets which respond to ajax]
|       |- DAO                   [DAOs]
|       |- JSON                  [Objects to be mapped to JSONs]
|       |- model                 [java beans to be passed to jsps]  
|       `- util                  [utility static classes]
`- web
    |- assets                    [for assets that are accessible by users]
    |    |- html                 [static pages, mainly for errors]
    |    |- css                  [css]
    |    `- js                   [js]
    |- images
    |   |- avatars               [avatars]
    |   |    `- default          [default avatars]
    |   `- covers                [covers]
    |        `- default          [default covers]
    `- WEB-INF
        |- lib
        |- res
        `- jsp                   [jsp files]
            `- shared            [shared jsp components]
```

## Database Schema
- **user**
(**id**, userName, nickname, firstName, lastName, dateOfBirth, email, signature, description, avatar, passwordHash, salt, iteration)

- **article**
(**id**, title, content, time, *userName, cover, isDeleted)

- **comment**
(**id**, content, time, *userName, *article, *parent, isDeleted,)

- **likeArticle**
(***userName**, ***article**)

- **likeComment**
(***userName**, ***comment**)

- **tag**
(***article**, **tag**)

## API Design
| url | type | description |
|:---|:---:|:---:|
| ~/ | html | displays the index page |
| ~/index | html | displays the index page |
| ~/indexPage |html| displays the index page |
| ~/signInPage |html| displays the sign in|
| ~/signUpPage|html| displays the sign up page|
| ~/editProfilePage |html| displays the profile editing page |
| ~/editArticlePage |html| displays the article editing/creating page|
| ~/articlesPage |html| displays the page showing user profile and all articles|
| ~/articlePage |html| displays a single article|
| ~/signOut |action| sign out, redirect to index|
| ~/signIn |action| submit sign in form, redirect to the original place|
| ~/signUp |action| submit sign up form, redirect to profile editing page|
| ~/saveProfile |action| submit file editing form, redirect to sign in page|
| ~/deleteAccount |action| submit account deletion form, redirect to index|
| ~/postArticle |action| submit article editing form, redirect to this article|
| ~/deleteArticle |action| submit article deletion form, redirect to all articles page|
| ~/postComment |action| submit comment creating form, returns a json response? |
| ~/deleteComment |action| submit comment deleting form, returns a json response? |
| ~/validateUserName |json| test whether the user name is available, returns a json response |

## Page Design

[Page prototypes on Axure](https://3uzaen.axshare.com/)

(password: pgcert)

- Index page (~/, ~/index, ~/indexPage)
    - **consumes**
        - isUserLoggedIn : Boolean
        - user : User?
        - articleSummaries : List\<ArticleSummaries\>
    
- Sign in page (~/signInPage)
   - **consumes**
       - isUserLoggedIn : Boolean
       - user : User?
       - hasLogInFail : Boolean?
   - **sends**
       - [post] to signIn
           - userName
           - password
           
- Sign up page (~/signUpPage)
   - **consumes**
       - [ajax] ValidateUserNameResult : JSON {"validateStatus" : "success|failure"}
   - **sends**
       - [post] to signUp
           - userName
           - password
       - [ajax] to validateUserName
           - userName
           
- Edit profile page (~/editProfilePage)
    - **consumes**
        - isUserLoggedIn : Boolean
        - user : User?
    - **sends**
        - [post] to saveProfile
            - id
            - userName
            - nickname
            - firstName
            - lastName
            - email
            - signature
            - description
            - avatar
            - dateOfBirth
            - originalAvatar
            - defaultAvatar
        - [post] to deleteAccount
            - id
    
- Edit article page (~/editArticlePage)
    - **consumes**
        - isUserLoggedIn : Boolean
        - user : User?
        - article : Article
    - **sends**
        - [post] to postArticle
            - id
            - title
            - content
            - cover
            - user
            - tags
            - originalCover
        - [post] to deleteArticle
            - id
    
- Articles page (~/articlesPage)
    - **consumes**
        - isUserLoggedIn : Boolean
        - user : User?
        - articleSummaries : List\<ArticleSummaries\>
    - **sends**
        - [get] to editArticlePage
            - id
        - [post] to deleteArticle
            -id
    
- Article page (~/articlePage)
    - **consumes**
        - isUserLoggedIn : Boolean
        - user : User?
        - article : Article
    - **sends**
        - [post] to deleteComment
            - id
            - articleId
        - [post] to postComment
            - article
            - parent
            - userName
            - content

- Authors page (~/authorsPage)
    - **consumes**
        - isUserLoggedIn: Boolean
        - user : User?
        - authors : List<Authors>