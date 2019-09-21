#! /bin/bash
app_dir=/home/ubuntu/app/MartHolidayAlarm
app_log_path=$app_dir/logs
mkdir -p $app_log_path
$app_dir/deploy.sh > $app_log_path/execution.log 2> /dev/null < /dev/null &