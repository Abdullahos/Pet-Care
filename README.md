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
    - If the code matches the functional, techniqual requirement, follows our design and ready to merge, i 'll merge it into the main branch, if not i 'll leave comments
## Technical Requirement
### Implementation Details
- Java Version: 17
- Spring Boot version: 3.2.0
- Database: h2
- Use Hibernate as ORM
- Sufficient unit test is a must
- Mock or just bypass the spring security for now
### Phase 1
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
### Phase 2
We introduce service request idea!
A service request is created when owner wants to make a reservation for his pet to some service, ie: cleaning
+----------------------------------+
|         ServiceRequest           |
+----------------------------------+
| - id: string   
| - number: string
| - petId: Long                     |
| - skills: List<Skill>             |
| - dueDate: Date                   |
| - assigned: boolean               |
| - assignmentDate: Date            |
| - employeeId: Long                |
+----------------------------------+

Implement a service to assign the free employee with the requird skills
The service must handle the happy paths(exactly one employee match), more than one, not at all
implement the service throgh all the layers: controller, service & repo
As usual unit test is a must!
