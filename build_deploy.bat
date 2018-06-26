@echo off
if not exist "target\nxj-classes\" (
	mkdir target\nxj-classes
)
call nxjc -d target/nxj-classes src/main/java/de/hbrs/designmethodik/cleanbot/*.java
cd target/nxj-classes
call nxjlink -o ../cleanbot.nxj de.hbrs.designmethodik.cleanbot.Application
cd ..
call nxjupload -r cleanbot.nxj
cd ..