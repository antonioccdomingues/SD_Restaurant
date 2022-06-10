xterm  -T "General Repository" -hold -e "./GeneralReposDeployAndRun.sh" &
sleep 6
xterm  -T "Bar" -hold -e "./BarDeployAndRun.sh" &
xterm  -T "Table" -hold -e "./TableDeployAndRun.sh" &
xterm  -T "Kitchen" -hold -e "./KitchenDeployAndRun.sh" &
sleep 6
xterm  -T "Chef" -hold -e "./ChefDeployAndRun.sh" &
xterm  -T "Waiter" -hold -e "./WaiterDeployAndRun.sh" &
xterm  -T "Student" -hold -e "./StudentDeployAndRun.sh" &
