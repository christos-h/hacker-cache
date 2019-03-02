echo "Restarting hacker-cache.service."

if [ ! -e /etc/systemd/system/hacker-cache.service ]
then
	echo "Cannot find hacker-cache.service. Exiting..."
	exit 1
fi

sudo systemctl restart hacker-cache.service

if [ $? -ne 0 ]
then
	echo "Could not restart hacker-cache service. Exiting..."
	exit 1
fi
echo "Success."
