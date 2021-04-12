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

### 生成镜像
```shell script
# 1.idea mvn
# 1.1 docker-maven-plugin插件，这种是免dockerfile的，执行后会直接生成镜像
mvn clean package docker:build
# 1.2 dockerfile-maven-plugin插件，这种是需要dockerfile的，并且要和pom在同一目录，执行后会直接生成镜像
mvn clean package dockerfile:build
# 2.或直接通过命令构建镜像，进入到docker文件目录（springboot的jar包也在此目录），latest是tag号，也可以是1.0等
docker build -t myapp:latest .
# 如果提示没有权限，需要修改docker的权限
sudo chmod 777 /var/run/docker.sock
```

### 上传，下载镜像到docker服务器[hub.docker.com](https://hub.docker.com)
```shell script
# 搜索镜像
docker search registry
# 添加registry镜像，只用配置一次
docker pull registry
# 登录，输入用户名和密码
docker login
# 上传镜像到hub.docker.com服务器，docker push 192.168.0.0.1:5000/chahongjing:testdocker:1.0
docker push chahongjing/testdocker:1.0
# 从服务器上获取镜像，docker pull 192.168.0.0.1:5000/chahongjing:testdocker:1.0
docker pull chahongjing/testdocker:1.0
```

### 运行docker
```shell script
# 运行镜像，8081是容器内容端口号，12345是容器对外端口号，通过 http://localhost:12345/index 访问
docker run --name=myspringbootapp -d -p 12345:8081 chahongjing/testdocker:1.0
# 查看容器运行实例id
docker ps -a
# 停止运行实例，参数为容器id
docker stop d8b5d84df51a
# 删除镜像文件
docker rmi -f myapp:latest
# 删除整个镜像实例，容器等
docker rm d8b5d84df51a
# 清理为none的镜像
docker rmi $(docker images | grep "none" | awk '{print $3}')
# 重启docker实例
docker restart d8b5d84df51a
```

### 其它
```shell script
# 进入容器
docker exec -it 容器名/ID bash //进入容器
-- 控制台查看日志
docker logs -f --tail=100 ca     # ca是containerid
-- 查看日志文件先要进到容器内部
```

### 服务器
[dockerhub](https://hub.docker.com/) <br>
[vultr](https://my.vultr.com)

# springmvc
### 三种方式
1. 直接将war复制到tomcat镜像内部：docker cp d_web.war 容器Id:/usr/local/tomcat/webapps
2. 使用war包外挂的方式：docker run -p 8081:8080 --name springmvc-maven-web-volume -v /opt/projects/springmvc-maven.war:/usr/local/tomcat/webapps/springmvc-maven.war -d tomcat
3. 使用dockfile
```shell script
FROM tomcat #基础镜像
MAINTAINER "zhoubang <842324724@qq.com>"  # 作者
ENV DIR_WEBAPP /usr/local/tomcat/webapps/ # 定义变量，后续会使用；该路径是tomcat容器中的webapps默认的路径
ADD springmvc-maven.war $DIR_WEBAPP/springmvc-maven.war # 将war添加到tomcat容器中的webapps目录下
RUN unzip $DIR_WEBAPP/springmvc-maven.war -d $DIR_WEBAPP # 解压war到DIR_WEBAPP参数对应的目录下
CMD ["catalina.sh", "run"] # 执行启动脚本，运行tomcat
```

[springmvc docker部署](https://www.yuque.com/zhoubang/docker/docker-deploy-springmvc-maven)