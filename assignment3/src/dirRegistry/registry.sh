CODEBASE="http://l040101-ws09.ua.pt/sd205/classes/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=true\
     -Djava.security.policy=java.policy\
     -classpath "../genclass.jar:."\
     serverSide.main.RegisterRemoteObjectMain 22349 l040101-ws09.ua.pt 22340
