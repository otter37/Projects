#ifndef __Lab14__SharedQueue__
#define __Lab14__SharedQueue__

#include <mutex>

template <typename T>
class SharedQueue {
public:
  SharedQueue();
  ~SharedQueue();
  
    /*
     These declarations mean that we do NOT get the automatically
     defined copy/copy-assign functions.  If you try to call one
     by copying the queue, you'll get a compiler error.
     
     This is a common technique for things that are uncopyable (like std::thread and std::mutex, for example).
     
     We really DO want a destructor, so cheating at the
     rule of 3 here makes sense here.
     */
    SharedQueue(const SharedQueue<T>&) = delete;
    SharedQueue<T>& operator=(const SharedQueue<T>&) = delete;

    
  //Return true if the queue is empty
  bool IsEmpty() const;
  
  //Enqueue the next item at the tail of the queue.
  void Add(T value);
  
  //Dequeue the next queue element and store it in "item" variable.  The function returns false if the queue is empty and no item can be retrieved.
  bool Remove(T &item);
  void Print();
private:
  struct QueueItem {
    T item;        QueueItem* prev;
	QueueItem* next;
  };
  QueueItem* head;
  QueueItem* tail;
  std::mutex queueLock;

  //Fill in the The private data members.
};

//Fill in the function definitions
template<typename T>
inline SharedQueue<T>::SharedQueue()
{
	head = nullptr;
	tail = nullptr;
}

template<typename T>
SharedQueue<T>::~SharedQueue()
{
	if (head == nullptr)
	{
		return;
	}
	while (!IsEmpty())
	{
		T item;
		Remove(item);
	};
}

template<typename T>
inline bool SharedQueue<T>::IsEmpty() const
{
	return (head == nullptr);
}

template<typename T>
void SharedQueue<T>::Add(T value)
{
	queueLock.lock();
	QueueItem* newItem = new QueueItem{ value, nullptr, head };
	if (tail == nullptr)
		tail = newItem;
	if (head != nullptr)
		head->prev = newItem;
	head = newItem;
	queueLock.unlock();
}

template<typename T>
bool SharedQueue<T>::Remove(T & item)
{
	queueLock.lock();
	if (tail == nullptr)
	{
		queueLock.unlock();
		return false;
	}
	QueueItem* queueItem = tail;
	item = queueItem->item;
	tail = queueItem->prev;
	if (tail != nullptr)
		tail->next = queueItem->next;
	else
		head = nullptr;
	delete queueItem;
	queueLock.unlock();
	return true;
}

template<typename T>
void SharedQueue<T>::Print()
{
	queueLock.lock();
	if (head == nullptr) {
		queueLock.unlock();
		std::cout << "Empty Queue" << std::endl;
		return;
	}
	QueueItem* current = head;
	while (current != nullptr) {
		std::cout << "(" << current->item << "), ";
	}
	std::cout << "\n";
	queueLock.unlock();
}

#endif