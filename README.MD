### 安装docker
``` shell script
sudo apt install docker.io
# 启动服务
sudo service docker start
# 重启服务
sudo service docker restart
# 关闭服务
sudo service docker stop
```

### springboot
```shell script
# 1.idea mvn
# 1.1 docker-maven-plugin插件，这种是免dockerfile的，执行后会直接生成镜像
mvn clean package docker:build
# 1.2 dockerfile-maven-plugin插件，这种是需要dockerfile的，并且要和pom在同一目录，执行后会直接生成镜像
mvn clean package dockerfile:build
# 2.或直接通过命令构建镜像，进入到docker文件目录（springboot的jar包也在此目录），latest是tag号，也可以是1.0等
docker build -t myapp:latest .
# 如果使用ARG命令，可以在构建参数添加 --build-arg JAR_FILE=myapp.jar
# 如果提示没有权限，需要修改docker的权限
sudo chmod 777 /var/run/docker.sock
```
### springmvc
#### 三种方式
1. 直接将war复制到tomcat镜像内部：docker cp ToolSiteMvc4J.war 容器Id:/usr/local/tomcat/webapps
2. 使用war包外挂的方式：docker run -p 8081:8080 --name springmvc-docker-volume -v /opt/projects/ToolSiteMvc4J.war:/usr/local/tomcat/webapps/ToolSiteMvc4J.war -d tomcat
3. 使用dockfile，注意，使用时需要把所有注释全部去掉
```shell script
# 创建docker file
## 基础镜像
FROM tomcat
MAINTAINER "zjy <310510906@qq.com>"
ENV DIR_WEBAPP /usr/local/tomcat/webapps/
## 将war添加到tomcat容器中的webapps目录下
ADD ToolSiteMvc4J.war $DIR_WEBAPP/ToolSiteMvc4J.war
## RUN unzip $DIR_WEBAPP/ToolSiteMvc4J.war -d $DIR_WEBAPP # 解压war到DIR_WEBAPP参数对应的目录下
CMD ["catalina.sh", "run"]
```
- 3.1 构建 
``` shell
# 构建镜像
docker build -t springmvctest:latest .
# 运行docker
docker run --name=myspringmvcapp -d -p 12346:8080 springmvctest
```


[springmvc docker部署](https://www.yuque.com/zhoubang/docker/docker-deploy-springmvc-maven)

### 上传，下载镜像到docker服务器[hub.docker.com](https://hub.docker.com)
```shell script
# 搜索镜像
docker search registry
# 添加registry镜像，只用配置一次
docker pull registry
# 登录，输入用户名和密码
docker login
# 上传镜像到hub.docker.com服务器，docker push 192.168.0.0.1:5000/chahongjing:testdocker:1.0
docker tag myapp chahongjing/myapp
docker push chahongjing/myapp
docker push chahongjing/testdocker:1.0
# 从服务器上获取镜像，docker pull 192.168.0.0.1:5000/chahongjing:testdocker:1.0
docker pull chahongjing/testdocker:1.0
```

### 运行docker
```shell script
# 运行镜像，8081是容器内容端口号，12345是容器对外端口号，通过 http://localhost:12345/index 访问
docker run --name=myspringbootapp -d -p 12345:8081 chahongjing/testdocker:1.0
docker run -it mvc /bin/bash
# 使用host模式，此模式完全复用主机网络，缺点是没有网络隔离，且会占用宿主机端口，外部无法访问host容器
docker run --name=mvc --network host -p 8088:8088 mvc:latest

# 查看容器运行实例id
docker ps -a
# 停止运行实例，参数为容器id
docker stop d8b5d84df51a
# 删除整个镜像实例，容器等
docker rm d8b5d84df51a
# 删除镜像文件
docker rmi -f myapp:latest

# 清理为none的镜像
docker rmi $(docker images | grep "none" | awk '{print $3}')
# 重启docker实例
docker restart d8b5d84df51a

docker images -aq
```

``` shell script
docker commit -a="作者" -m="备注" 容器id newname:[tag]
匿名挂载-v /容器目录；具名挂载-v vname:/容器目录；指定目录挂载：-v /宿主机目录:/容器目录
docker volume inspect 卷名
docker inspect 镜像名称或Id

docker network insptect 容器名
CMD：定容器启动时要执行的命令，只有最后一个会生效，可被替代
ENTRYPOINT：指定容器启动时要执行的命令,可以追加命令

MAINTAINER zjy<310@qq.com>
ENV MY_PATH /home/work/mydir
RUN mkdir $MY_PATH
RUN yum install vim
WORKDIR $MY_PATH
COPY /mydir $MY_PATH
ADD jdk.tar.gz /usr/local/
ENV JAVA_HOME /usr/local/jdk


docker run --link 要连通的容器名。link其实是在hosts中添加了网络映射，是单向的
docker network create -d bridge --subnet 192.168.0.0/16 mynet
docker network connect mynet 容器名
docker run --net=mynet
数据卷容器
docker run --volumes-from 被共享的容器名
```

### 其它
```shell script
# 进入容器
docker exec -it 容器名/ID bash //进入容器
docker exec -it f89 /bin/bash
-- 控制台查看日志
docker logs -f --tail=100 ca     # ca是containerid
-- 查看日志文件先要进到容器内部
```

### 服务器
[dockerhub](https://hub.docker.com/) <br>
[vultr](https://my.vultr.com)

### docker ui
```shell script
docker search portainer
docker pull portainer/portainer
docker volume create portainer_data
docker run -d -p 9000:9000 --name portainer --restart always -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data portainer/portainer
```

### 接口注释
[genDoc](https://github.com.cnpmjs.org/easycodingnow/genDoc)

[k8s](http://www.dockone.io/article/932)


1. docker compose
2. docker swarm