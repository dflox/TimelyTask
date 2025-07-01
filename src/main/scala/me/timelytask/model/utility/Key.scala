package me.timelytask.model.utility

sealed trait Key

object Key {
  val keyMap: Map[String, Key] = Map(
    MoveUp.toString -> MoveUp,
    MoveDown.toString -> MoveDown,
    MoveLeft.toString -> MoveLeft,
    MoveRight.toString -> MoveRight,
    Home.toString -> Home,
    End.toString -> End,
    PageUp.toString -> PageUp,
    PageDown.toString -> PageDown,
    Insert.toString -> Insert,
    Delete.toString -> Delete,
    Backspace.toString -> Backspace,
    Tab.toString -> Tab,
    Space.toString -> Space,
    Enter.toString -> Enter,
    Dot.toString -> Dot,
    Comma.toString -> Comma,
    Colon.toString -> Colon,
    Semicolon.toString -> Semicolon,
    Plus.toString -> Plus,
    Minus.toString -> Minus,
    Slash.toString -> Slash,
    Backslash.toString -> Backslash,
    F1.toString -> F1,
    F2.toString -> F2,
    F3.toString -> F3,
    F4.toString -> F4,
    F5.toString -> F5,
    F6.toString -> F6,
    F7.toString -> F7,
    F8.toString -> F8,
    F9.toString -> F9,
    F10.toString -> F10,
    F11.toString -> F11,
    F12.toString -> F12,
    CtrlUp.toString -> CtrlUp,
    CtrlDown.toString -> CtrlDown,
    CtrlLeft.toString -> CtrlLeft,
    CtrlRight.toString -> CtrlRight,
    ShiftUp.toString -> ShiftUp,
    ShiftDown.toString -> ShiftDown,
    ShiftLeft.toString -> ShiftLeft,
    ShiftRight.toString -> ShiftRight,
    AltUp.toString -> AltUp,
    AltDown.toString -> AltDown,
    AltLeft.toString -> AltLeft,
    AltRight.toString -> AltRight,
    CtrlA.toString -> CtrlA,
    CtrlB.toString -> CtrlB,
    CtrlC.toString -> CtrlC,
    CtrlD.toString -> CtrlD,
    CtrlE.toString -> CtrlE,
    CtrlF.toString -> CtrlF,
    CtrlG.toString -> CtrlG,
    CtrlH.toString -> CtrlH,
    CtrlI.toString -> CtrlI,
    CtrlJ.toString -> CtrlJ,
    CtrlK.toString -> CtrlK,
    CtrlL.toString -> CtrlL,
    CtrlM.toString -> CtrlM,
    CtrlN.toString -> CtrlN,
    CtrlO.toString -> CtrlO,
    CtrlP.toString -> CtrlP,
    CtrlQ.toString -> CtrlQ,
    CtrlR.toString -> CtrlR,
    CtrlS.toString -> CtrlS,
    CtrlT.toString -> CtrlT,
    CtrlU.toString -> CtrlU,
    CtrlV.toString -> CtrlV,
    CtrlW.toString -> CtrlW,
    CtrlX.toString -> CtrlX,
    CtrlY.toString -> CtrlY,
    CtrlZ.toString -> CtrlZ,
    CtrlPlus.toString -> CtrlPlus,
    CtrlMinus.toString -> CtrlMinus,
    CtrlSpace.toString -> CtrlSpace,
    CtrlF1.toString -> CtrlF1,
    CtrlF2.toString -> CtrlF2,
    CtrlF3.toString -> CtrlF3,
    CtrlF4.toString -> CtrlF4,
    CtrlF5.toString -> CtrlF5,
    CtrlF6.toString -> CtrlF6,
    CtrlF7.toString -> CtrlF7,
    CtrlF8.toString -> CtrlF8,
    CtrlF9.toString -> CtrlF9,
    CtrlF10.toString -> CtrlF10,
    CtrlF11.toString -> CtrlF11,
    CtrlF12.toString -> CtrlF12,
    Ctrl1.toString -> Ctrl1,
    Ctrl2.toString -> Ctrl2,
    Ctrl3.toString -> Ctrl3,
    Ctrl4.toString -> Ctrl4,
    Ctrl5.toString -> Ctrl5,
    Ctrl6.toString -> Ctrl6,
    Ctrl7.toString -> Ctrl7,
    Ctrl8.toString -> Ctrl8,
    Ctrl9.toString -> Ctrl9,
    Ctrl0.toString -> Ctrl0,
    Alt1.toString -> Alt1,
    Alt2.toString -> Alt2,
    Alt3.toString -> Alt3,
    Alt4.toString -> Alt4,
    Alt5.toString -> Alt5,
    Alt6.toString -> Alt6,
    Alt7.toString -> Alt7,
    Alt8.toString -> Alt8,
    Alt9.toString -> Alt9,
    Alt0.toString -> Alt0,
    ShiftOne.toString -> ShiftOne,
    ShiftTwo.toString -> ShiftTwo,
    ShiftThree.toString -> ShiftThree,
    A.toString -> A,
    B.toString -> B,
    C.toString -> C,
    D.toString -> D,
    E.toString -> E,
    F.toString -> F,
    G.toString -> G,
    H.toString -> H,
    I.toString -> I,
    J.toString -> J,
    K.toString -> K,
    L.toString -> L,
    M.toString -> M,
    N.toString -> N,
    O.toString -> O,
    P.toString -> P,
    Q.toString -> Q,
    R.toString -> R,
    S.toString -> S,
    T.toString -> T,
    U.toString -> U,
    V.toString -> V,
    W.toString -> W,
    X.toString -> X,
    Y.toString -> Y,
    Z.toString -> Z,
    Num0.toString -> Num0,
    Num1.toString -> Num1,
    Num2.toString -> Num2,
    Num3.toString -> Num3,
    Num4.toString -> Num4,
    Num5.toString -> Num5,
    Num6.toString -> Num6,
    Num7.toString -> Num7,
    Num8.toString -> Num8,
    Num9.toString -> Num9,
    Unknown.toString -> Unknown
  )
  
  def fromString(s: String): Key = {
    keyMap.getOrElse(s, Unknown)
  }

// Basic movement
  case object MoveUp extends Key

  case object MoveDown extends Key

  case object MoveLeft extends Key

  case object MoveRight extends Key

// Special keys
  case object Home extends Key

  case object End extends Key

  case object PageUp extends Key

  case object PageDown extends Key

  case object Insert extends Key

  case object Delete extends Key

  case object Backspace extends Key

  case object Tab extends Key

// Function keys
  case object F1 extends Key

  case object F2 extends Key

  case object F3 extends Key

  case object F4 extends Key

  case object F5 extends Key

  case object F6 extends Key

  case object F7 extends Key

  case object F8 extends Key

  case object F9 extends Key

  case object F10 extends Key

  case object F11 extends Key

  case object F12 extends Key

// Modified keys
  case object CtrlUp extends Key

  case object CtrlDown extends Key

  case object CtrlLeft extends Key

  case object CtrlRight extends Key

  case object ShiftUp extends Key

  case object ShiftDown extends Key

  case object ShiftLeft extends Key

  case object ShiftRight extends Key

  case object AltUp extends Key

  case object AltDown extends Key

  case object AltLeft extends Key

  case object AltRight extends Key

  case object CtrlA extends Key

  case object CtrlB extends Key

  case object CtrlC extends Key

  case object CtrlD extends Key

  case object CtrlE extends Key

  case object CtrlF extends Key

  case object CtrlG extends Key

  case object CtrlH extends Key

  case object CtrlI extends Key

  case object CtrlJ extends Key

  case object CtrlK extends Key

  case object CtrlL extends Key

  case object CtrlM extends Key

  case object CtrlN extends Key

  case object CtrlO extends Key

  case object CtrlP extends Key

  case object CtrlQ extends Key

  case object CtrlR extends Key

  case object CtrlS extends Key

  case object CtrlT extends Key

  case object CtrlU extends Key

  case object CtrlV extends Key

  case object CtrlW extends Key

  case object CtrlX extends Key

  case object CtrlY extends Key

  case object CtrlZ extends Key

  case object CtrlPlus extends Key

  case object CtrlMinus extends Key

  case object CtrlSpace extends Key

  case object CtrlF1 extends Key
  case object CtrlF2 extends Key
  case object CtrlF3 extends Key
  case object CtrlF4 extends Key
  case object CtrlF5 extends Key
  case object CtrlF6 extends Key
  case object CtrlF7 extends Key
  case object CtrlF8 extends Key
  case object CtrlF9 extends Key
  case object CtrlF10 extends Key
  case object CtrlF11 extends Key
  case object CtrlF12 extends Key
  
  case object Ctrl1 extends Key
  case object Ctrl2 extends Key
  case object Ctrl3 extends Key
  case object Ctrl4 extends Key
  case object Ctrl5 extends Key
  case object Ctrl6 extends Key
  case object Ctrl7 extends Key
  case object Ctrl8 extends Key
  case object Ctrl9 extends Key
  case object Ctrl0 extends Key
  
  case object Alt1 extends Key
  case object Alt2 extends Key
  case object Alt3 extends Key
  case object Alt4 extends Key
  case object Alt5 extends Key
  case object Alt6 extends Key
  case object Alt7 extends Key
  case object Alt8 extends Key
  case object Alt9 extends Key
  case object Alt0 extends Key
  
  case object ShiftOne extends Key
  case object ShiftTwo extends Key
  case object ShiftThree extends Key

// Letter keys
  case object A extends Key

  case object B extends Key

  case object C extends Key

  case object D extends Key

  case object E extends Key

  case object F extends Key

  case object G extends Key

  case object H extends Key

  case object I extends Key

  case object J extends Key

  case object K extends Key

  case object L extends Key

  case object M extends Key

  case object N extends Key

  case object O extends Key

  case object P extends Key

  case object Q extends Key

  case object R extends Key

  case object S extends Key

  case object T extends Key

  case object U extends Key

  case object V extends Key

  case object W extends Key

  case object X extends Key

  case object Y extends Key

  case object Z extends Key

// Number keys

  case object Num0 extends Key

  case object Num1 extends Key

  case object Num2 extends Key

  case object Num3 extends Key

  case object Num4 extends Key

  case object Num5 extends Key

  case object Num6 extends Key

  case object Num7 extends Key

  case object Num8 extends Key

  case object Num9 extends Key

// Other

  case object Space extends Key

  case object Enter extends Key

  case object Dot extends Key

  case object Comma extends Key

  case object Colon extends Key

  case object Semicolon extends Key

  case object Plus extends Key

  case object Minus extends Key

  case object Slash extends Key

  case object Backslash extends Key

  case object Unknown extends Key
}
