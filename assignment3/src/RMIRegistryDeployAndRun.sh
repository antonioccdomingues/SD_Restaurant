echo "Transfering data to the RMIregistry node."
sshpass -p "qwerty" ssh sd205@l040101-ws09.ua.pt 'mkdir -p test/Restaurant'
sshpass -p "qwerty" ssh sd205@l040101-ws09.ua.pt 'rm -rf test/Restaurant/*'
sshpass -p "qwerty" ssh sd205@l040101-ws09.ua.pt 'mkdir -p Public/classes/interfaces'
sshpass -p "qwerty" ssh sd205@l040101-ws09.ua.pt 'rm -rf Public/classes/interfaces/*'
sshpass -p "qwerty" ssh sd205@l040101-ws09.ua.pt 'mkdir -p Public/classes/commInfra'
sshpass -p "qwerty" ssh sd205@l040101-ws09.ua.pt 'rm -rf Public/classes/commInfra/*'
sshpass -p "qwerty" scp -r genclass.jar dirRMIRegistry.zip sd205@l040101-ws09.ua.pt:/home/sd205/test/Restaurant
echo "Decompressing data sent to the RMIregistry node."
sshpass -p "qwerty" ssh -q sd205@l040101-ws09.ua.pt "cd test/Restaurant ; unzip -uq dirRMIRegistry.zip"
echo "Executing program at the RMIregistry node."
sshpass -p "qwerty" ssh sd205@l040101-ws09.ua.pt 'cd test/Restaurant/dirRMIRegistry ; cp interfaces/*.class /home/sd205/Public/classes/interfaces ; cp commInfra/*.class /home/sd205/Public/classes/commInfra ; cp rmiregistry.sh /home/sd205'
sshpass -p "qwerty" ssh -q sd205@l040101-ws09.ua.pt './rmiregistry.sh 22150'


