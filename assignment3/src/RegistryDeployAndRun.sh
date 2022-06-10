echo "Transfering data to the Registry node."
sshpass -p "qwerty" ssh sd205@l040101-ws09.ua.pt 'pkill -U sd205 java'
sshpass -p "qwerty" ssh sd205@l040101-ws09.ua.pt 'mkdir -p test/Restaurant'
sshpass -p "qwerty" ssh sd205@l040101-ws09.ua.pt 'rm -rf test/Restaurant/*'
sshpass -p "qwerty" scp -r genclass.jar dirRegistry.zip sd205@l040101-ws09.ua.pt:/home/sd205/test/Restaurant
echo "Decompressing data sent to the registry node."
sshpass -p "qwerty" ssh -q sd205@l040101-ws09.ua.pt "cd test/Restaurant ; unzip -uq dirRegistry.zip"
echo "Executing program at the registry node."
sshpass -p "qwerty" ssh -q sd205@l040101-ws09.ua.pt 'cd test/Restaurant/dirRegistry ; ./registry.sh'