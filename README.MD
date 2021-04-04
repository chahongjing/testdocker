### 安装docker
``` shell script
sudo apt install docker.io
```

### 生成镜像
```shell script
# idea mvn
mvn clean package docker:build
# 或直接通过命令构建镜像，进入到docker文件目录（springboot的jar包也在此目录），latest是tag号，也可以是1.0等
docker build -t myapp:latest .
# 如果提示没有权限，需要修改docker的权限
sudo chmod 777 /var/run/docker.sock
```

### 运行docker
```shell script
# 运行镜像，8080是容器内容端口号，12345是容器对外端口号，通过 http://localhost:12345/index 访问
docker run --name=myspringbootapp -d -p 12345:8080 myapp:latest
# 查看容器运行实例id
docker ps -a
# 停止运行实例，参数为容器id
docker stop d8b5d84df51a
# 删除镜像文件
docker rmi -f myapp:latest
# 删除整个镜像实例，容器等
docker rm d8b5d84df51a
```

### 其它
```shell script
-- 控制台查看日志
docker logs -f --tail=100 ca     # ca是containerid
-- 查看日志文件先要进到容器内部

https://blog.csdn.net/duangecho/article/details/80315316
```

### jenkins
``` shell
#!/bin/bash 

#export BUILD_ID=dontKillMe这一句很重要，这样指定了，项目启动之后才不会被Jenkins杀掉。
export BUILD_ID=dontKillMe

#指定最后编译好的jar存放的位置
www_path=/var/codespace/test/

#Jenkins中编译好的jar位置
jar_path=/var/lib/jenkins/jobs/shaw/workspace/shaw-web/target/

#Jenkins中编译好的jar名称
jar_name=shaw.jar

#获取运行编译好的进程ID，便于我们在重新部署项目的时候先杀掉以前的进程
pid=$(cat /var/codespace/test/shaw-test-web.pid)

#进入指定的编译好的jar的位置
cd  ${jar_path}

#将编译好的jar复制到最后指定的位置
cp  ${jar_path}/${jar_name} ${www_path}

#进入最后指定存放jar的位置
cd  ${www_path}

#杀掉以前可能启动的项目进程
kill -9 ${pid}

#启动jar，指定SpringBoot的profiles为test,后台启动
java -jar -Dspring.profiles.active=test ${jar_name} &

#将进程ID存入到shaw-web.pid文件中
echo $! > /var/codespace/test/shaw-test-web.pid
```