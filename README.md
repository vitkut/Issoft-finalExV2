# Issoft-finalExV2
  Задание:
Есть многоэтажное здание (этажность конфигурируема). В здании есть лифты (количество конфигурируемо). На каждом этаже есть кнопки вызова "вверх" и "вниз" (общие для всех лифтов). На каждом этаже рандомно появляются люди (рандомной массы), которые хотят ехать на другой этаж (рандомный). Интенсивность генерации людей конфигурируема. 
У каждого лифта есть грузоподъемность, скорость и скорость открытия/закрытия дверей. 
У человека есть масса тела и этаж на который ему нужно.
Люди стоят в очереди на засадку в лифты (одна очередь вверх, одна вниз) не нарушая ее. Приехав на нужный этаж, человек исчезает. 

Необходимо реализовать непрерывно работающее приложение (люди появляются, вызывают лифт и едут на нужный этаж) используя многопоточность (Thread, wait, notify, sleep).
По желанию можно использовать java.util.concurrent. Так же описать выбранный алгоритм текстом (кратко). 
// тесты, maven, логгирование
// stream API
// реализовать сбор статистики (сколько людей перевезено каждым лифтом с каким этажей и на какие этажи)
// логировать основные события системы (что бы по логам можно было следить за тем, что происходит)
// рекомендация: реализовать логику управления лифтами и покрыть ее тестами без потоков, а потом подключить многопоточность

  Алгоритм работы:
Контроль направления движения поездов и контроль действий людей реализованы в классе Controller. 
Этот класс имеет методы controlFloors (контроллер проверяет каждый этаж на наличие в нем людей, и если находит - добавляет в лист ControllerEvent'ов
ивент (если там такого еще нет) с номером этажа, где расположен человек, и направлением, куда ему нужно ехать),
clearEventListDuplicates (контроллер проверяет для каждого ControllerEvent'а, нет ли его уже в списке ивентов одного из лифтов, а если есть - ControllerEvent удаляется),
checkElevatorEvents (проверяет ControllerEvent'ы для каждого лифта на то, будет ли лифту по пути. Если лист ElevatorEvent'ов лифта пуст или,
если направление движения лифта такое же, как и в ControllerEvent'е, и позиция лифта < позиции в ControllerEvent'е для движения вверх 
или позиция лифта < позиции в ControllerEvent'е для движения вниз, то ControllerEvent вставляется в ивенты лифта в нужное место с помощью метода insertEventToElevatorsEvents),
humanControl (контролирует движение людей, если у лифта открыты двери (т.е. он стоит). Сначала люди, чей это этаж, выходят из лифта, а потом люди на этаже, которым по пути,
заходят в лифт (при этом в ивенты лифта добавляется новый ивент от каждого зашедшего человека)).