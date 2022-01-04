# Supplementary specification

## Functionalities

_Specifies the features that are not related to the cases of use, namely: Audit, Reporting and Security._

**Safety:**
  
* **The interactions of the aforementioned users (i.e. Admin, Manager, Collaborator) must be preceded by an authentication process.**
* **The use of the platform by other people is restricted to the registration of organizations.**


## Usability

_Evaluates the user interface. It has several subcategories,
among them: error prevention; aesthetics and design; Help and
documentation; consistency and standards._

-


## Reliability
_Refers to the integrity, compliance and interoperability of the software. The requirements to be considered are: frequency and severity of failure, possibility of recovery, possibility of prediction, accuracy, average time between failures._

-


## Performance
_Evaluates the performance requirements of the software, namely: response time, memory consumption, CPU usage, load capacity and application availability._

-


## Supportability
_The supportability requirements group several characteristics, such as:
testability, adaptability, maintainability, compatibility,
configurability, instability, scalability and more._

**Configurability:**

* **The platform's commercial name and other data that may be relevant must be specified by a configuration at the time of its implementation.**
* **External algorithm (password generator) configured only when the system was implemented.**
* **Therefore,the system should be prepared to use any e­mail service/API**

**Testability:**

* **[...] specify a relevant set of coverage and mutation tests (e.g. unitary, functional and integration) [...]**

# +

### Design restrictions

_Specifies or restricts the system design process. Examples may include: programming languages, software process, use of development tools, class library, etc._

* **Adopt good practices for identifying requirements and analyzing and designing OO software**

* **Reuse the existing user management component at T4J**

* **Passwords must be generated [...] using an external algorithm (i.e. designed by third parties) and configured only when deploying of the system.**


### Implementation restrictions

_Specifies or restricts the code or construction of a system such
mandatory standards, implementation languages,
database integrity, resource limits, operating system._

* **Implement the core software core in Java and the user interface should be implemented using JavaFX**
* **Adopt recognized coding standards**

### Interface restrictions

_Specifies or restricts the functionalities inherent to the interaction of the
system with other external systems._

* **Passwords [...] must be generated [...] using an external algorithm (i.e. designed by a third party).**
* **Therefore,the system should be prepared to use any e­mail service/API**

### Physical restrictions

_Specifies a limitation or physical requirement of the hardware used, for
example: material, shape, size or weight._

-
