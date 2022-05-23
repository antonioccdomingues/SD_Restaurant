mkdir -p bin
javac -cp genclass.jar commInfra/*.java serverSide/*/*.java -d bin
javac -cp genclass.jar commInfra/*.java clientSide/*/*.java -d bin
mkdir -p /tmp/commInfra
mkdir -p /tmp/serverSide
mkdir -p /tmp/clientSide
cp -rf bin/commInfra/* /tmp/commInfra
cp -rf bin/serverSide/* /tmp/serverSide
cp -rf bin/clientSide/* /tmp/clientSide
a=$(pwd)
cd /tmp
zip -rq $a/bar.zip serverSide/main/BarMain.class serverSide/main/Constants.class serverSide/entities/ServiceProviderAgent.class serverSide/sharedRegions/Bar.class  serverSide/sharedRegions/BarMessageExchange.class serverSide/sharedRegions/SharedRegionInterface.class serverSide/entities/* serverSide/stubs/* commInfra/*
zip -rq $a/table.zip serverSide/main/TableMain.class serverSide/main/Constants.class serverSide/entities/ServiceProviderAgent.class serverSide/sharedRegions/Table.class serverSide/sharedRegions/TableMessageExchange.class serverSide/sharedRegions/SharedRegionInterface.class serverSide/entities/* serverSide/stubs/* commInfra/*
zip -rq $a/kitchen.zip serverSide/main/KitchenMain.class serverSide/main/Constants.class serverSide/entities/ServiceProviderAgent.class serverSide/sharedRegions/Kitchen.class serverSide/sharedRegions/KitchenMessageExchange.class serverSide/sharedRegions/SharedRegionInterface.class serverSide/entities/* serverSide/stubs/* commInfra/*
zip -rq $a/generalRepos.zip serverSide/main/GeneralReposMain.class serverSide/main/Constants.class serverSide/entities/ServiceProviderAgent.class serverSide/sharedRegions/GeneralRepos.class serverSide/sharedRegions/GeneralReposMessageExchange.class serverSide/sharedRegions/SharedRegionInterface.class commInfra/* serverSide/entities/*States.class
zip -rq $a/chef.zip clientSide/main/ChefMain.class clientSide/main/Constants.class clientSide/stubs/* clientSide/entities/Chef.class clientSide/entities/ChefState.class commInfra/*
zip -rq $a/waiter.zip clientSide/main/WaiterMain.class clientSide/main/Constants.class clientSide/stubs/* clientSide/entities/Waiter.class clientSide/entities/WaiterState.class commInfra/*
zip -rq $a/student.zip clientSide/main/StudentMain.class clientSide/main/Constants.class clientSide/stubs/* clientSide/entities/Student.class clientSide/entities/StudentState.class commInfra/*
cd $a