# noc-autocoder
The National Occupation Classification (NOC) Autocoder API is use to classify occupations. Identifying an occupation with the NOC can provide labour market information (LMI) in your region such as available jobs, wages, employment outlooks and to get a sense of the Canadian labour market. The NOC Autocoder API uses Machine Learning to classify occupations (4-digit level NOC) with plain language such as familiar job titles, qualifications, skills, knowledge, tools and technology lexicon. The classifier model was built with production-grade open-source technology (Linux/Java) for scalability and enterprise interoperability. Apache Mahout is the Machine Learning framework of choice. It is a powerful, scalable machine-learning library that runs on top of Hadoop (Big Data). Apache Hadoop is an open source software platform for distributed storage and distributed processing of very large data sets on computer clusters built from commodity hardware. Apache Mahout is a project of the Apache Software Foundation to produce free implementations of distributed or otherwise scalable machine learning algorithms focused primarily in the areas of collaborative filtering, clustering and classification.  

### Technology Stack:

- **Operating Systems**
  + CentOS 7 (Linux)
  + JVM (Java Virtual Machine)
- **Data Science**
  + Apache Hadoop
  + Apache Mahout
  + Java

### Datasets

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[Download NOC 2016 datasets ](https://open.canada.ca/data/dataset/f1f287de-1208-490d-9faf-302d343df0eb)

### Data Science Environment Setup

1. You can install Hadoop on a single machine/VM or on a cluster (multinode).

  + [Single-Node Hadoop Installation on CentOS 7](https://github.com/LMID-DIMT/noc-autocoder/wiki/Single-Node-Hadoop-Installation-on-CentOS-7)

  + [Multi-Node Hadoop Installation on CentOS 7](https://github.com/LMID-DIMT/noc-autocoder/wiki/Multi-Node-Hadoop-Installation-on-CentOS-7)

2. [Installing and configuring Apache Mahout for Hadoop](https://github.com/LMID-DIMT/noc-autocoder/wiki/Installing-and-configuring-Apache-Mahout-for-Hadoop)

### Building the Classifier Model

_Work in progress (Coming Soon)_

### Classifier Model Evaluation

_Work in progress (Coming Soon)_

### Productionizing Machine Learning Model

_Work in progress (Coming Soon)_
