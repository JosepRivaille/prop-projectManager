JFLAGS = -d
RFLAGS = -rf
JC = javac
RM = rm
TARGET = ./classes
JARS = $(wildcard ./libs/*.jar)
LIB = $(TARGET):$(shell echo '$(JARS)' | sed 's/jar /jar:/g')
CLASSPATH = -classpath 

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $(TARGET) $(CLASSPATH) $(LIB) $*.java
	
SOURCEPATH = .
CLASSES = \
	src/main/java/edu/upc/fib/prop/exceptions/AuthorNotFoundException.java \
	src/main/java/edu/upc/fib/prop/exceptions/UserNotFoundException.java \
	src/main/java/edu/upc/fib/prop/exceptions/InvalidDetailsException.java \
	src/main/java/edu/upc/fib/prop/exceptions/AlreadyExistingUserException.java \
	src/main/java/edu/upc/fib/prop/exceptions/AlreadyExistingDocumentException.java \
	src/main/java/edu/upc/fib/prop/exceptions/DocumentNotFoundException.java \
	src/main/java/edu/upc/fib/prop/exceptions/ImportExportException.java \
	src/main/java/edu/upc/fib/prop/exceptions/InvalidQueryException.java \
	src/main/java/edu/upc/fib/prop/utils/Constants.java \
	src/main/java/edu/upc/fib/prop/utils/Strings.java \
	src/main/java/edu/upc/fib/prop/utils/FileUtils.java \
	src/main/java/edu/upc/fib/prop/utils/IOUtils.java \
	src/main/java/edu/upc/fib/prop/models/Author.java \
	src/main/java/edu/upc/fib/prop/models/Document.java \
	src/main/java/edu/upc/fib/prop/models/DocumentBasicInfo.java \
	src/main/java/edu/upc/fib/prop/models/User.java \
	src/main/java/edu/upc/fib/prop/models/WeightsVector.java \
	src/main/java/edu/upc/fib/prop/models/DocumentsSet.java \
	src/main/java/edu/upc/fib/prop/models/DocumentsCollection.java \
	src/main/java/edu/upc/fib/prop/models/SortedDocumentsSet.java \
	src/main/java/edu/upc/fib/prop/models/AuthorsCollection.java \
	src/main/java/edu/upc/fib/prop/models/Operator.java \
	src/main/java/edu/upc/fib/prop/models/NodeType.java \
	src/main/java/edu/upc/fib/prop/models/BooleanExpressionNode.java \
	src/main/java/edu/upc/fib/prop/utils/StringUtils.java \
	src/main/java/edu/upc/fib/prop/utils/ImportExport.java \
	src/main/java/edu/upc/fib/prop/persistence/dao/authors/DaoAuthors.java \
	src/main/java/edu/upc/fib/prop/persistence/dao/authors/impl/DaoAuthorsImpl.java \
	src/main/java/edu/upc/fib/prop/persistence/dao/users/DaoUsers.java \
	src/main/java/edu/upc/fib/prop/persistence/dao/users/impl/DaoUsersImpl.java \
	src/main/java/edu/upc/fib/prop/persistence/dao/documents/DaoDocuments.java \
	src/main/java/edu/upc/fib/prop/persistence/dao/documents/impl/DaoDocumentsImpl.java \
	src/main/java/edu/upc/fib/prop/persistence/controllers/PersistenceController.java \
	src/main/java/edu/upc/fib/prop/persistence/controllers/impl/PersistenceControllerImpl.java \
	src/main/java/edu/upc/fib/prop/business/users/UsersManager.java \
	src/main/java/edu/upc/fib/prop/business/users/impl/UsersManagerImpl.java \
	src/main/java/edu/upc/fib/prop/business/search/SearchDocument.java \
	src/main/java/edu/upc/fib/prop/business/search/impl/SearchDocumentImpl.java \
	src/main/java/edu/upc/fib/prop/business/search/SearchAuthor.java \
	src/main/java/edu/upc/fib/prop/business/search/impl/SearchAuthorImpl.java \
	src/main/java/edu/upc/fib/prop/business/search/SearchBooleanExpression.java \
	src/main/java/edu/upc/fib/prop/business/search/impl/SearchBooleanExpressionImpl.java \
	src/main/java/edu/upc/fib/prop/business/controllers/BusinessController.java \
	src/main/java/edu/upc/fib/prop/business/controllers/impl/BusinessControllerImpl.java \
	src/main/java/edu/upc/fib/prop/view/controllers/ViewGraphicController.java \
	src/main/java/edu/upc/fib/prop/view/controllers/impl/ViewGraphicControllerImpl.java \
	src/main/java/edu/upc/fib/prop/utils/Java2JavascriptUtils.java \
	src/main/java/edu/upc/fib/prop/App.java

CLASSESTEST = \
	src/main/java/edu/upc/fib/prop/drivers/DriversUtils.java \
	src/main/java/edu/upc/fib/prop/drivers/DriversBooleanSearch.java \
	src/main/java/edu/upc/fib/prop/drivers/DriversDecisionTree.java

CLS = $(CLASSES:.java=.class)
CLST = $(CLASSESTEST:.java=.class)

all: $(CLS)

buildJar:
	jar cmvf ./META-INF/MANIFEST_MAIN.MF prop-projectManager.jar -C $(TARGET)/ .

buildTests: $(CLST)
	jar cmvf ./META-INF/MANIFEST_BOOLEAN.MF driversBooleanSearch.jar -C $(TARGET)/ .
	jar cmvf ./META-INF/MANIFEST_DEC_TREE.MF driversDecisionTree.jar -C $(TARGET)/ .

run:
	java -cp ./classes edu.upc.fib.prop.App

runJar:
	java -jar prop-projectManager.jar

clean:
	$(RM) $(RFLAGS) $(TARGET)/edu
	$(RM) $(RFLAGS) *.jar
