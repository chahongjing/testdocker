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