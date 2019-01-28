# noc-autocoder
The National Occupation Classification (NOC) Autocoder API is use to classify occupations. Identifying an occupation with the NOC can provide labour market information (LMI) in your region such as available jobs, wages, employment outlooks and to get a sense of the Canadian labour market. The NOC Autocoder API uses Machine Learning to classify occupations (4-digit level NOC) with plain language such as familiar job titles, qualifications, skills, knowledge, tools and technology lexicon. The classifier model was built with production-grade open-source technology (Linux/Java) for scalability and enterprise interoperability. Apache Mahout is the Machine Learning framework of choice. It is a powerful, scalable machine-learning library that runs on top of Hadoop (Big Data). Apache Hadoop is an open source software platform for distributed storage and distributed processing of very large data sets on computer clusters built from commodity hardware. Apache Mahout is a project of the Apache Software Foundation to produce free implementations of distributed or otherwise scalable machine learning algorithms focused primarily in the areas of collaborative filtering, clustering and classification.  

### Technology Stack:

- **Operating Systems**
  + CentOS 7 (Linux)
  + JVM (Java Virtual Machine)
- **Data Science**
  + Apache Hadoop
  + Apache Mahout
  + Java Enterprise (J2EE)
- **Web Service (Production)**
  + Jersey (RESTful API)
  + Apache Tomcat


**Update:**
The new version of noc-autocoder classifier model uses Apache Spark as its computing engine replacing Hadoop MapReduce. Apache Spark is the recommended out-of-the-box distributed back-end, or can be extended to other distributed backends. Apache Mahout now supports Modular Native Solvers for CPU/GPU/CUDA acceleration.

### Datasets

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[Download NOC 2016 datasets ](https://open.canada.ca/data/dataset/f1f287de-1208-490d-9faf-302d343df0eb)

### Data Science Environment Setup

+ Depending on your computing requirement and the volume of data used for processing, Hadoop provides a very flexible and scalable solution to suit your needs. Steps for installing Apache Hadoop on a single machine/VM or on a cluster (multinode) are provided below.

  + [Single-Node Hadoop Installation on CentOS 7](https://github.com/LMID-DIMT/noc-autocoder/wiki/Single-Node-Hadoop-Installation-on-CentOS-7) (Single node computing)

  + [Multi-Node Hadoop Installation on CentOS 7](https://github.com/LMID-DIMT/noc-autocoder/wiki/Multi-Node-Hadoop-Installation-on-CentOS-7) (Parallel computing for Big Data)

+ Apache Mahout is a scalable production-grade open-source Machine Learning library that runs on top of Hadoop. Mahout has established itself as a frontrunner in the field of machine learning technologies and has currently been adopted by prominent tech companies like Twitter, Facebook, Yahoo, Foursquare, Intel, Adobe, Linked.in, and the list goes on.

  + [Installing and configuring Apache Mahout for Hadoop](https://github.com/LMID-DIMT/noc-autocoder/wiki/Installing-and-configuring-Apache-Mahout-for-Hadoop)

### Preparing the training set for Machine Learning

  + Data cleaning and preprocessing

    + remove non-ASCII characters
    + normalize text  _(i.e. convert all text to lowercase, remove accents)_
    + remove stop-words
    + remove irrelevant keywords _(e.g. city names, province names, etc.)_
    + word stemming
    + fix malformed words
    + strip extra white spaces


  + Feature extraction
    + convert directory of documents to sequence file format
    + create vectors from sequence file
    + create normalized TF-IDF vectors using ngram models

### Selecting a text classification algorithm

The  **Naive  Bayes**  algorithm  is  a  probabilistic  classification  algorithm.  It  makes  its  decisions  about  which  class  to  assign  to  an  input  document  using  probabilities  derived  from  training  data.  The  training  process  analyzes  the relationship between words in the training documents and categories, and then categories and the entire training set. The available facts are collected using calculations based on Bayes Theorem to produce the probability that a collection of words (a document) belongs in a certain class.

### Loading the training set into Hadoop

1. Copy preprocessed training set from local machine to HDFS _(Hadoop Distributed File System)_

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[Download the NOC 2016 preprocessed dataset](https://github.com/LMID-DIMT/noc-autocoder/tree/master/noc-autocoder-mahout/data/noc2016_corpus)

Example:
```
$ hdfs dfs -copyFromLocal ~/raw_data/noc2016_corpus /user/servo/raw_data
```

### Building and testing the NOC Autocoder classifier model

[Step by step guide in building the NOC2016 Autocoder classifier model](https://github.com/LMID-DIMT/noc-autocoder/wiki/Building-the-Classifier-Model-with-Mahout)



### Building applications and scaling Machine Learning models for production

+ Architecture
  + [Implementation of a scalable enterprise-grade Machine Learning applications to production environment](https://github.com/LMID-DIMT/noc-autocoder/blob/master/docs/DataScience-Architecture.pdf)
+ Machine Learning Workflow
  + [Machine Learning Workflow using parallel computing and Big Data](https://github.com/LMID-DIMT/noc-autocoder/blob/master/docs/Machine-Learning-Workflow.pdf)
+ Productionizing Machine Learning Models
  + _Work in progress (Coming Soon)_

### Practical and real-world implementations of the NOC Autocoder Machine Learning model in production

+ Production-ready:

  + [Resume/CV to NOC Recommender](https://clmi-explore-icmt.ca/viz?page=home&lang=en) - A tool that recommends occupations with your resume/CV as input. Click the _Find a NOC code_ button and copy-paste your resume/CV to get occupation recommendations.

  + [Chatbot LISA](https://clmi-explore-icmt.ca/viz?page=chat-lisa) - L.I.S.A. is the _LMI Information and Support Assistant_ that presents Labour Market Information with interactive data visualization using conversational user interface. Extending the NOC Autocoder capability by integrating A.I./NLP (Natural Language Processing) to handle human-like conversation between man and machine.

  + [Chatbot NIC](https://clmi-explore-icmt.ca/viz?page=chat-nic) - N.I.C. is _NOC Information Companion_ that helps you find your NOC using plain language.

  + NOC Autocoder RESTful API - Available as a web service.

  + Facebook Messenger Bots featuring NIC and LISA

+ Proof-of-concept

  + Alexa Skill for LMI - Getting LMI through Alexa enabled device

  + RocketChat LISA Chatbot - Porting LISA to RocketChat using Hubot template




***
#### Reference:

+ [https://mahout.apache.org](https://mahout.apache.org/)

+ [https://en.wikipedia.org/wiki/Apache_Hadoop](https://en.wikipedia.org/wiki/Apache_Hadoop)

+ [https://spark.apache.org](https://spark.apache.org/)

+ [https://jersey.github.io/](https://jersey.github.io/)

+ [https://clmi-explore-icmt.ca/viz?page=tech&lang=en#ai](https://clmi-explore-icmt.ca/viz?page=tech&lang=en#ai)
