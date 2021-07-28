#include<iostream>
#include<stack>
#include<limits.h>
using namespace std;

unsigned int dsum(unsigned int i){
    if(i==0) return 0;
    else{
        return (i%10) + dsum(i/10); 
    }
}

void dispStack(stack<int> s){
    while(s.empty()!=true){
        cout<<s.top()<<" ";
        s.pop();
    }
    cout<<endl;
}

void popMinMax(stack<int> s){
    stack<int> temp;
    int min=INT_MAX;
    int max=INT_MIN;
    while(s.empty() != true){
        int i = s.top();
        if(max<i) max=i;
        if(min>i) min=i;
        temp.push(i);
        s.pop();
    }

    while(temp.empty() != true){
        int i = temp.top();
        if(i!=max && i!=min){
            s.push(i);
        }
        temp.pop();
    }

    dispStack(s);
}

main(){
    stack<int> s;
    s.push(1);
    s.push(3);
    s.push(2);
    s.push(4);
    s.push(1);
    s.push(3);
    s.push(2);
    s.push(4);
    popMinMax(s);
}