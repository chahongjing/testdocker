FROM python:3.8-alpine3.12
#FROM python:2.7-alpine
ENV MY_PATH="/tmp/mvc/"
WORKDIR $MY_PATH
COPY ./* ./

#RUN pip install -r requirements.txt

ENTRYPOINT ["python", "python.py"]