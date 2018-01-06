#!/bin/bash

#

USER="cube"

# add user
id -u ${USER} &>/dev/null || useradd ${USER}

# register shell
SHELL="/srv/pa-audio-cube/bin/audio-cube-shell"

if [ "$(cat /etc/shells|grep ${SHELL})" != "${SHELL}" ]; then
  #echo "shell not registered"
  echo ${SHELL} >> /etc/shells
fi

# change shell of user
chsh -s ${SHELL} ${USER}

# create autologin configuration
# information found at:
# https://maker-tutorials.com/raspberry-pi-benutzer-automatisch-anmelden-booten/
AUTO_LOGIN_CONF="/etc/systemd/system/getty@tty1.service.d/autologin.conf"
if [ ! -e ${AUTO_LOGIN_CONF} ]; then
  echo "[Service]" >> ${AUTO_LOGIN_CONF}
  echo "ExecStart=" >> ${AUTO_LOGIN_CONF}
  echo "ExecStart=-/sbin/agetty --autologin ${USER} --noclear %I 38400 linux" >> ${AUTO_LOGIN_CONF}
fi
