
# Pet-Care
Saas Project to manage a Pet Care place
## Some notes:
- When I say or write implement, I mean Please Design, Implement, and Test
- Branch Name Convention: ${ProjectName-developerName-taskNo}, ex: PetCare-abdullah-1
- Commits Convention: ${projectName-developerName-taskNo}/some descriptive description :D

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
