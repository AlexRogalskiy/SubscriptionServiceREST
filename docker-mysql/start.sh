#! /bin/bash
set -e

MYSQL_ROOT_PWD=${MYSQL_ROOT_PWD:-"mysql"}
MYSQL_USER=${MYSQL_USER:-"root"}
MYSQL_USER_PWD=${MYSQL_USER_PWD:-"toor"}
MYSQL_USER_DB=${MYSQL_USER_DB:-""}

echo "[*] Setting up mysql credentials"
service mysql start $ sleep 10

echo "[*] Setting up root new password"
mysql --user=root --password= -e "UPDATE mysql.user set authentication_string=password('$MYSQL_ROOT_PWD') where user='root'; FLUSH PRIVILEGES;"

echo "[*] Setting up root remote password"
mysql --user=root --password=$MYSQL_ROOT_PWD -e "GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '$MYSQL_ROOT_PWD' WITH GRANT OPTION; FLUSH PRIVILEGES;"

if [ -n "$MYSQL_USER_DB" ]; then
	echo "[*] Creating default datebase: $MYSQL_USER_DB"
	mysql --user=root --password=$MYSQL_ROOT_PWD -e "CREATE DATABASE IF NOT EXISTS \`$MYSQL_USER_DB\` CHARACTER SET utf8 COLLATE utf8_general_ci; FLUSH PRIVILEGES;"

	if [ -n "$MYSQL_USER" ] && [ -n "$MYSQL_USER_PWD" ]; then
		echo "[*] Creating new user: $MYSQL_USER for the database $MYSQL_USER_DB"
		mysql --user=root --password=$MYSQL_ROOT_PWD -e "GRANT ALL PRIVILEGES ON \`$MYSQL_USER_DB\`.* TO '$MYSQL_USER'@'%' IDENTIFIED BY '$MYSQL_USER_PWD' WITH GRANT OPTION; FLUSH PRIVILEGES;"
	else
		echo "[*] The user credentials are not provided"
	fi
else
	if [ -n "$MYSQL_USER" ] && [ -n "$MYSQL_USER_PWD" ]; then
		echo "[*] Creating new user: $MYSQL_USER for all databases"
		mysql --user=root --password=$MYSQL_ROOT_PWD -e "GRANT ALL PRIVILEGES ON *.* TO '$MYSQL_USER'@'%' IDENTIFIED BY '$MYSQL_USER_PWD' WITH GRANT OPTION; FLUSH PRIVILEGES;"
	else
		echo "[*] The user credentials are not provided"
	fi
fi

killall mysqld
sleep 4
echo "[*] Configuration settings have been successfully applied"

exec "$@"