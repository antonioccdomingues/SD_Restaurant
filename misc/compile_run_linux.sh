#Only use for the deliery, as the paths have to be adjusted
javac -classpath ./genclass.jar ./entities/*.java ./main/*.java ./sharedRegions/*.java ./FIFO/*.java
java -cp ".:./genclass.jar" main.MainProgram 