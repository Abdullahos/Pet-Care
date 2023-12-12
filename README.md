# Pet-Care
Saas Project to manage a Pet Care place
## Some notes:
- When I say or write implement, I mean Please Design, Implement, and Test
- Branch Name Convention: ${ProjectName-developerName-taskNo}, ex: PetCare-abdullah-1
- Commits Convention: ${projectName-developerName-taskNo}/some descriptive description :D
- When we given a task we :
    - Got the requirements (Make sure we and the requirements are aligned)
    - Create new branch (usually from the main or develop)
    - Design, Implement, and test the feature
    - Commit and push to that branch, and create PR(pull request) into main/sometime main or other branch we will be told
    - The PM, tester, or someone eligable doing functional testing
    - Code Review (usually by me)
    - If the code matches the functional, techniqual requirement, follows our design and ready to merge, i 'll merge it into the main branch, if not i leave comments
## Technical Requirement
### Implementation Details
- Java Version: 17
- Spring Boot version: 2.7.0
- Database: h2
- Use Hibernate as ORM
- Sufficient unit test is a must
- Mock or just bypass the spring security for now

- Implement Pet, Owner and Employee entities and their association
- Implement CRUD Rest Apis for all entities
```
+--------------+        +--------------+       +-------------+
|     Pet      |        |    Owner    |        |   Employee  |
+--------------+        +-------------+        +-------------+
| -id: Long    |        | -id: Long    |       | -id: Long   |
| -name: String|        | -name: String|       | -name: String|
| -species: String|     | -contact: String|    | -email: String|
| -owner: Owner|<>------| -pets: List<Pet>|    | -password: String|
+--------------+        +----------------+     +------------------+
```

