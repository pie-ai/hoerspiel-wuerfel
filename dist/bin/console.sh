#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

PARENT_DIR=$( dirname ${DIR}.. )
CONF_DIR="${PARENT_DIR}/conf"
LIBS_DIR="${PARENT_DIR}/libs"

SYSTEM_PROPERTIES="-Dapplication.home=${PARENT_DIR}"
#SYSTEM_PROPERTIES="${SYSTEM_PROPERTIES} -Dfoo=bar"

# calculate class path
LIBS=$(find ${LIBS_DIR}/|grep server)
ls -la ${LIBS_DIR}/
CLASSPATH="${CONF_DIR}";
for LIB in $(echo ${LIBS}); do
	if [ -f ${LIB} ]; then
		CLASSPATH="${CLASSPATH}:${LIB}"
	fi
done

MAINCLASS="de.pa2.projects.audio.cube.CommandLineInterface"
JAVA="java"

COMMAND="${JAVA} -cp ${CLASSPATH} ${SYSTEM_PROPERTIES} ${MAINCLASS}"
cd ${PARENT_DIR}
#echo "executing: '${COMMAND}'"
${COMMAND}
