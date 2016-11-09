JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	# Models #
	src/main/java/edu/upc/fib/prop/models/Author.java \
	src/main/java/edu/upc/fib/prop/models/DocumentsCollection.java \
	src/main/java/edu/upc/fib/prop/models/User.java \
	src/main/java/edu/upc/fib/prop/models/DocumentsSet.java \
	src/main/java/edu/upc/fib/prop/models/SortedDocumentsSet.java \
	src/main/java/edu/upc/fib/prop/models/AuthorsCollection.java \
	src/main/java/edu/upc/fib/prop/models/WeightsVector.java \
	src/main/java/edu/upc/fib/prop/models/Document.java \
	# Exceptions #
	src/main/java/edu/upc/fib/prop/exceptions/UserNotFoundException.java \
	src/main/java/edu/upc/fib/prop/exceptions/DocumentNotFoundException.java \
	src/main/java/edu/upc/fib/prop/exceptions/AlreadyExistingUserException.java \
	src/main/java/edu/upc/fib/prop/exceptions/InvalidDetailsException.java \
	src/main/java/edu/upc/fib/prop/exceptions/AuthorNotFoundException.java \
	src/main/java/edu/upc/fib/prop/exceptions/AlreadyExistingDocumentException.java \
	# Utils #
	src/main/java/edu/upc/fib/prop/utils/FileUtils.java \
	src/main/java/edu/upc/fib/prop/utils/StringUtils.java \
	src/main/java/edu/upc/fib/prop/utils/MenuTree.java \
	src/main/java//edu/upc/fib/prop/utils/IOUtils.java \
	src/main/java/edu/upc/fib/prop/utils/Constants.java \
	# Persistence #
	src/main/java/edu/upc/fib/prop/persistence/controllers/PersistenceController.java \
	src/main/java/edu/upc/fib/prop/persistence/controllers/impl/PersistenceControllerImpl.java \
	src/main/java/edu/upc/fib/prop/persistence/dao/authors/impl/DaoAuthorsImpl.java \
	src/main/java/edu/upc/fib/prop/persistence/dao/authors/DaoAuthors.java \
	src/main/java/edu/upc/fib/prop/persistence/dao/users/impl/DaoUsersImpl.java \
	src/main/java/edu/upc/fib/prop/persistence/dao/users/DaoUsers.java \
	src/main/java/edu/upc/fib/prop/persistence/dao/documents/DaoDocuments.java \
	src/main/java/edu/upc/fib/prop/persistence/dao/documents/impl/DaoDocumentsImpl.java \
	# Business #
	src/main/java/edu/upc/fib/prop/business/controllers/BusinessController.java \
	src/main/java/edu/upc/fib/prop/business/controllers/impl/BusinessControllerImpl.java \
	src/main/java/edu/upc/fib/prop/business/users/UsersManager.java \
	src/main/java/edu/upc/fib/prop/business/users/impl/UsersManagerImpl.java \
	src/main/java/edu/upc/fib/prop/business/search/SearchDocument.java \
	src/main/java/edu/upc/fib/prop/business/search/SearchAuthor.java \
	src/main/java/edu/upc/fib/prop/business/search/impl/SearchDocumentImpl.java \
	src/main/java/edu/upc/fib/prop/business/search/impl/SearchAuthorImpl.java \
	src/main/java/edu/upc/fib/prop/business/documents/DocumentTools.java \
	# View #
	src/main/java/edu/upc/fib/prop/view/controllers/impl/ViewControllerImpl.java \
	src/main/java/edu/upc/fib/prop/view/controllers/ViewController.java \
	src/main/java/edu/upc/fib/prop/view/document/DocumentManager.java \
	src/main/java/edu/upc/fib/prop/view/menu/MainMenu.java \
	# App #
	src/main/java/edu/upc/fib/prop/App.java


classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
